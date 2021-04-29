# Packages supported
# sgx : SGX runtime package.

### common ###

require sgx-common_2.12.inc

# Packages required to build.
DEPENDS += "protobuf-native cppmicroservices-native curl protobuf cppmicroservices sgx-sdk sgx-sdk-cross"

### patch ###

SRC_URI += " \
    file://0031-add-sysroot-libdir-target.patch  \
    file://0032-cppmicroservices-target-build.patch \
"

### compile ###

# Build target(s)
EXTRA_OEMAKE += "psw_install_pkg"

do_compile_prepend () {
    source ${RECIPE_SYSROOT}${sgxsdkpath}-cross/environment
}

### install ###

do_install () {
    # Package PSW installer
    install -d "${D}${sgxdirprefix}${sgxrootdir}"
    install -m 0755 "${B}/linux/installer/bin/sgx_linux_x64_psw_2.12.100.3.bin" "${D}${sgxdirprefix}${sgxrootdir}"
}

### package ###

SYSROOT_DIRS += "${sgxrootdir}"
FILES_${PN}  += "${sgxrootdir}/sgx_linux_x64_psw_2.12.100.3.bin"

pkg_postinst_ontarget_${PN} () {
    "${sgxrootdir}/sgx_linux_x64_psw_2.12.100.3.bin"
    rm -f "${sgxrootdir}/sgx_linux_x64_psw_2.12.100.3.bin"
}

### ###

# Runtime dependencies
RDEPENDS_${PN} += "bash"
RRECOMMENDS_${PN} += "kernel-module-isgx"

CVE_PRODUCT = "software_guard_extensions_sdk"

