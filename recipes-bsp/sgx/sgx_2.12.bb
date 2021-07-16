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
    export SGX_SDK="${RECIPE_SYSROOT}${sgxsdkpath}-cross"
    export PATH=$PATH:$SGX_SDK/bin:$SGX_SDK/bin/x64
}

### install ###

do_install () {
    # Package PSW installer
    install -d "${D}${sgxdirprefix}${sgxrootdir}/package"
    install -m 0755 "${RECIPE_SYSROOT}${sgxrootdir}/package/sgx_linux_x64_sdk_2.12.100.3.bin" "${D}${sgxdirprefix}${sgxrootdir}/package"
    install -m 0755 "${B}/linux/installer/bin/sgx_linux_x64_psw_2.12.100.3.bin" "${D}${sgxdirprefix}${sgxrootdir}/package"

    # Package PSW installer
}

### package ###

SYSROOT_DIRS += "${sgxrootdir}"
FILES_${PN}  += "${sgxrootdir}"

# TODO: Fix script or implement install on first boot.
# This script causes fist boot to hang.
#pkg_postinst_ontarget_${PN} () {

#    Install SDK (optional)
#    if [ -f "${sgxrootdir}/package/sgx_linux_x64_sdk_2.12.100.3.bin" ]; then
#        "${sgxrootdir}/package/sgx_linux_x64_sdk_2.12.100.3.bin" --prefix /opt/intel
#    fi

#    Install PSW
#    "${sgxrootdir}/package/sgx_linux_x64_psw_2.12.100.3.bin"

#    Install DCAP libraries (optional)
#    if [ -f "${D}/opt/intel/package/sgx-dcap-libs.tar" ]; then
#        tar xvf "${D}/opt/intel/package/sgx-dcap-libs.tar" -C "/opt/intel/sgxpsw/aesm"
#    fi

#    Remove install binaries
#    rm -rf "${sgxrootdir}/package"

#}

### ###

# Runtime dependencies
RDEPENDS_${PN} += "bash"

CVE_PRODUCT = "software_guard_extensions_platform"

