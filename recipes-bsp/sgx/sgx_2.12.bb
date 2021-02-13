# Packages supported
# sgx : SGX runtime package.

### common ###

require sgx-common_2.12.inc

# Packages required to build.
DEPENDS_append_class-native += " curl-native protobuf-native sgx-sdk-native"
DEPENDS_append_class-target += " curl protobuf-native protobuf sgx-sdk"

### patch ###

SRC_URI += " \
    file://0031-add-sysroot-libdir-target.patch  \
"

# Build environment variables
sysrootpath_class-native = "${RECIPE_SYSROOT_NATIVE}"
sysrootpath_class-target = "${RECIPE_SYSROOT}"

### compile ###

# Build target(s)
EXTRA_OEMAKE += "psw_install_pkg"

do_compile_prepend () {
    . ${sysrootpath}${sgxsdkpath}/environment
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
    rm "${sgxrootdir}/sgx_linux_x64_psw_2.12.100.3.bin"
}

### ###

BBCLASSEXTEND = "native"

#SYSTEMD_SERVICE_${PN} = "aesmd.service"

# Runtime dependencies
RDEPENDS_${PN} += "bash"
RRECOMMENDS_${PN} += "kernel-module-isgx"

CVE_PRODUCT = "software_guard_extensions_sdk"

