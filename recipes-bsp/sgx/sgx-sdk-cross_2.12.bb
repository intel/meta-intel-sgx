# Packages supported
# sgx-sdk-cross : SGX Software Development Kit (SDK) on build host.

inherit native

### common ###

require sgx-common_2.12.inc

# Packages required to build.
DEPENDS += "ocaml-native ocamlbuild-native"
DEPENDS += "sgx-sdk"

### patch ###

SRC_URI += " \
    file://0011-add-sysroot-libdir-native.patch    \
"

### compile ###

# Build environment variables
EXTRA_OEMAKE += "OCAMLLIB='${STAGING_LIBDIR_NATIVE}/ocaml' -C sdk signtool edger8r sgx_encrypt"

### install ###

do_install () {
    install -d ${D}${sgxdirprefix}${sgxrootdir}
    cp -r ${RECIPE_SYSROOT}${sgxsdkpath} ${D}${sgxdirprefix}${sgxsdkpath}-cross
    rm -f ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64/*
    install -m 0755 ${B}/build/linux/sgx_sign    ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64
    install -m 0755 ${B}/build/linux/sgx_edger8r ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64
    install -m 0755 ${B}/build/linux/sgx_encrypt ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64
}

### ###

SYSROOT_DIRS += "${sgxdirprefix}${sgxrootdir}"
FILES_${PN}-dev += "${sgxsdkpath}-cross"

CVE_PRODUCT = "software_guard_extensions_sdk"

