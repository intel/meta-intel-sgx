# Packages supported
# sgx-sdk  : SGX Software Development Kit (SDK).

### common ###

require sgx-common_2.18.1.inc

# Packages required to build.
DEPENDS += "ocaml-native ocamlbuild-native autoconf-native automake-native"

### patch ###

SRC_URI += " \
    file://0001-sdk-configure.patch              \
    file://0002-install-sysroot.patch            \
    file://0021-fix-include-path.patch           \
"

### compile ###

# Build environment variables
EXTRA_OEMAKE += "OCAMLLIB='${STAGING_LIBDIR_NATIVE}/ocaml' sdk_install_pkg"
CFLAGS:append = " -Wno-deprecated-declarations"
CXXFLAGS:append = " -Wno-deprecated-declarations"

### install ###

do_install () {
    mkdir -p "${D}${sgxdirprefix}${sgxrootdir}/package"
    cp "${S}/linux/installer/bin/sgx_linux_x64_sdk_2.18.101.1.bin" "${D}${sgxdirprefix}${sgxrootdir}/package"
}

### package ###

SYSROOT_DIRS += "${sgxdirprefix}${sgxrootdir}/package"

FILES:${PN}-dev += "${sgxrootdir}"

### ###

CVE_PRODUCT = "software_guard_extensions_sdk"

