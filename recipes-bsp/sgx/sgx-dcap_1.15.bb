# Packages supported
# sgx-dcap : SGX Data Center Attestation Package.

### common ###

SUMMARY = "Intel(R) SGX Data Center Attestation Primitives (DCAP) driver"
LICENSE = "BSD & Intel-Redistributable-Binaries"
LIC_FILES_CHKSUM = "file://License.txt;md5=92c8e45a85591e3c219bceebaad7235e"

# Packages required to build.
DEPENDS += "curl sgx-sdk-cross protobuf protobuf-native boost protobuf-c protobuf-c-native"
#DEPENDS += "curl sgx-sdk-cross protobuf"

# Source locations
S = "${WORKDIR}/git"
D_prebuilt_dcap = "${S}/QuoteGeneration"
D_sgxssl="${S}/QuoteVerification/sgxssl"
D_openssl="${S}/QuoteVerification/sgxssl/openssl_source"

SRC_URI  = "git://github.com/intel/SGXDataCenterAttestationPrimitives.git;protocol=https"
SRCREV = "85cf8bdd393ab273a308be3f41d2f7cc25c0ec0c"

SRC_URI += " file://0033-fix-dcap-protobuf.patch"
### prebuilt sgx dcap source ###
SRC_URI += "https://download.01.org/intel-sgx/sgx-dcap/1.15/linux/prebuilt_dcap_1.15.tar.gz;name=prebuilt_dcap;subdir=${D_prebuilt_dcap}"
SRC_URI[prebuilt_dcap.sha256sum] = "b7a16cd939ec632363bb69f8df0bf60835d06adafb70e15ba341ef4e1d37ed36"

### configure sgxssl ###
S_sgxssl="${WORKDIR}/sgxssl"
File_sgxssl="lin_2.18_1.1.1q"
SRC_URI += "https://github.com/01org/intel-sgx-ssl/archive/${File_sgxssl}.zip;unpack=0;name=sgxssl;subdir=${WORKDIR}/sgxssl"
SRC_URI[sgxssl.sha256sum] = "6c33d2178b6b01bdbb1f97804ae14aec13544b0cb45902a0906c20ef7b4032bc"

configure_sgxssl () {
    # Copy intel-sgx-ssl to correct build location.
    if [ ! -d "${D_sgxssl}/openssl_source" ]; then
        if [ ! -d "${D_sgxssl}" ]; then
            echo "Create ${D_sgxssl}"
            mkdir -p "${D_sgxssl}"
        fi
        unzip "${S_sgxssl}/${File_sgxssl}.zip" -d "${D_sgxssl}-unzip"
        mv ${D_sgxssl}-unzip/intel-sgx-ssl-${File_sgxssl}/* "${D_sgxssl}"
        rmdir "${D_sgxssl}-unzip/intel-sgx-ssl-${File_sgxssl}"
        rmdir "${D_sgxssl}-unzip"
    fi
}

### configure openssl ###
# sgxssl requires openssl-1.1.1g for compilation and liniking, hence installing to sgx WORKDIR.
S_openssl="${WORKDIR}/openssl"
#File_openssl="openssl-1.1.1g"
File_openssl="openssl-1.1.1q"
#SRC_URI += "https://www.openssl.org/source/${File_openssl}.tar.gz;unpack=0;name=openssl;subdir=${S_openssl}"
SRC_URI += "https://www.openssl.org/source/${File_openssl}.tar.gz;unpack=0;name=openssl;subdir=${S_openssl}"
SRC_URI[openssl.sha256sum] = "d7939ce614029cdff0b6c20f0e2e5703158a489a72b2507b8bd51bf8c8fd10ca"

configure_openssl () {
    # Copy openssl to correct build location
    if [ ! -f "${D_openssl}/${File_openssl}.tar.gz" ]; then
        cp "${S_openssl}/${File_openssl}.tar.gz" "${D_openssl}"
        if [ -d "${D_openssl}/${File_openssl}" ]; then
            echo "Removing previous openssl copy (probably from previous build)"
            rm -rf "${D_openssl}/${File_openssl}" || true
        fi
    fi
}

### configure ###

do_configure () {
    configure_sgxssl
    configure_openssl
}

### compile ###

# Build target(s)
EXTRA_OEMAKE += "QuoteGeneration"

do_compile:prepend () {
    export SGX_SDK="${RECIPE_SYSROOT}/opt/intel/sgxsdk-cross"
    export PATH=$PATH:$SGX_SDK/bin:$SGX_SDK/bin/x64
    export LIB_PATH="${RECIPE_SYSROOT}-native/usr/lib"
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$LIB_PATH
}

### install ###

do_install () {
    install -d "${D}/opt/intel/package"
    cd "${S}/QuoteGeneration/build/linux"
    tar cvf "${D}/opt/intel/package/sgx-dcap-libs.tar" *.so
}

### package ###

SYSROOT_DIRS += "/opt/intel"
FILES:${PN}  += "/opt/intel"

### ###

# Race condition causes first build to fail.
PARALLEL_MAKE = ""

CVE_PRODUCT = "software_guard_extensions_data_center_attestation_primitives"

