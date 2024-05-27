# Packages supported
# sgx-sdk-cross : SGX Software Development Kit (SDK) on build host.
# Compatible only with x64 host

inherit native

### common ###

require sgx-common_2.18.1.inc

# Packages required to build.
DEPENDS += "ocaml-native ocamlbuild-native"
DEPENDS += "sgx-sdk"
CFLAGS:append = " -Wno-deprecated-declarations"
CXXFLAGS:append = " -Wno-deprecated-declarations"

### patch ###

SRC_URI += " \
    file://0011-add-sysroot-libdir-native.patch    \
"

### compile ###

# Build environment variables
EXTRA_OEMAKE += "OCAMLLIB='${STAGING_LIBDIR_NATIVE}/ocaml' -C sdk signtool edger8r sgx_encrypt"

### install ###

do_install () {
    ${RECIPE_SYSROOT}${sgxrootdir}/package/sgx_linux_x64_sdk_2.18.101.1.bin --prefix="${D}${sgxdirprefix}${sgxrootdir}"
    mv "${D}${sgxdirprefix}${sgxsdkpath}" "${D}${sgxdirprefix}${sgxsdkpath}-cross"
    rm -f ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64/*
    install -m 0755 ${B}/build/linux/sgx_sign    ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64
    install -m 0755 ${B}/build/linux/sgx_edger8r ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64
    install -m 0755 ${B}/build/linux/sgx_encrypt ${D}${sgxdirprefix}${sgxsdkpath}-cross/bin/x64

    # fix broken symbolic link
    if [ -L ${D}${sgxdirprefix}${sgxsdkpath}-cross/lib64/libsgx_urts.so.2 ]; then
        cd ${D}${sgxdirprefix}${sgxsdkpath}-cross/lib64
        ln -sf libsgx_urts.so libsgx_urts.so.2
    fi
    # Fix for qa check buildpaths that contains reference to TMPDIR. Files .pc have
    # TMPDIR "<absolute-path>/build/tmp-*/work/x86_64-linux/sgx-sdk-cross/2.18.1/recipe-sysroot-native/opt/intel/sgxsdk" in path to prefix variable.
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_quote_ex.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_epid_sim.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_urts_sim.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_epid.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_launch_sim.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_launch.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_uae_service_sim.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_uae_service.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_quote_ex_sim.pc
    sed -i -e 's#${D}${STAGING_DIR_NATIVE}##g' ${D}${sgxdirprefix}${sgxsdkpath}-cross/pkgconfig/libsgx_urts.pc
}

### ###

SYSROOT_DIRS += "${sgxdirprefix}${sgxsdkpath}-cross"
FILES:${PN}-dev += "${sgxsdkpath}-cross"

CVE_PRODUCT = "software_guard_extensions_sdk"

