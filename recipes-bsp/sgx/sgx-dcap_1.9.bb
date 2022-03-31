# Packages supported
# sgx-dcap : SGX Data Center Attestation Package.

### common ###

SUMMARY = "Intel(R) SGX Data Center Attestation Primitives (DCAP) driver"
LICENSE = "BSD & Intel-Redistributable-Binaries"
LIC_FILES_CHKSUM = "file://License.txt;md5=66da782d3c2c41b9669340fb2704ac59"

# Packages required to build.
DEPENDS += "curl sgx-sdk-cross"

# Source locations
S = "${WORKDIR}/git"
D_prebuilt_dcap = "${S}/QuoteGeneration"
D_sgxssl="${S}/QuoteVerification/sgxssl"
D_openssl="${S}/QuoteVerification/sgxssl/openssl_source"

SRC_URI  = "git://github.com/intel/SGXDataCenterAttestationPrimitives.git;protocol=https"
SRCREV = "98976322e8b58e23256355f5cf90b9e30e37d8c1"

### prebuilt sgx dcap source ###
SRC_URI += "https://download.01.org/intel-sgx/sgx-dcap/1.9/linux/prebuilt_dcap_1.9.tar.gz;name=prebuilt_dcap;subdir=${D_prebuilt_dcap}"
SRC_URI[prebuilt_dcap.sha256sum] = "8cd0249ee49dbfd589b257cd0fa14d374d01b6c210ca7d0a14e618a48bbdb82b"

### configure sgxssl ###
S_sgxssl="${WORKDIR}/sgxssl"
File_sgxssl="lin_2.11_1.1.1g"
SRC_URI += "https://github.com/01org/intel-sgx-ssl/archive/${File_sgxssl}.zip;unpack=0;name=sgxssl;subdir=${WORKDIR}/sgxssl"
SRC_URI[sgxssl.sha256sum] = "12828839c4555e0f5e88e86db090c995053d98d99091862c498fc55f379183fc"

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
File_openssl="openssl-1.1.1g"
SRC_URI += "https://www.openssl.org/source/${File_openssl}.tar.gz;unpack=0;name=openssl;subdir=${S_openssl}"
SRC_URI[openssl.sha256sum] = "ddb04774f1e32f0c49751e21b67216ac87852ceb056b75209af2443400636d46"

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

