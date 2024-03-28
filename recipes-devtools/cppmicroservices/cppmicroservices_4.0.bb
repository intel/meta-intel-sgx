inherit cmake

SUMMARY = "C++ Micro Services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Packages required to build
DEPENDS:append:class-target = " cppmicroservices-native"

# Parallel make does not work with some recipes
PARALLEL_MAKE = ""
PARALLEL_MAKEINST = ""

### source ###

# Source repo
SRC_URI = "gitsm://github.com/intel/linux-sgx.git;branch=master;protocol=https"
SRCREV = "984f3c9fe809b8d0acfb0b0934087c240ecf280f"

# Patches
SRC_URI:append:class-target = " file://0001-host-bin.patch \
                               "
SRC_URI:append = " file://0001-fix-build-with-gcc-11.patch;striplevel=3 \
                   file://0001-include-cstdint-header-to-fix-gcc13-build.patch;striplevel=3"
# Source directory
S = "${WORKDIR}/git/external/CppMicroServices"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT  = "1"

### configure ###

HOST_BIN = "${datadir}/cppmicroservices4/host_bin"

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=RelWithDebInfo"
EXTRA_OECMAKE:append:class-target = " -DCMAKE_HOST_BINARY_DIR:PATH=${RECIPE_SYSROOT_NATIVE}${HOST_BIN}"

### install ###

do_install:append () {
    rm -rfv ${D}${libdir}/.debug
}

do_install:append:class-native () {
    install -d "${D}${datadir}/cppmicroservices4/host_bin"
    install -m 0644 "${B}/ImportExecutables.cmake" "${D}${HOST_BIN}"
    install -m 0755 "${B}/bin/usResourceCompiler4" "${D}${HOST_BIN}"
}

do_install:append:class-target () {
    if [ "${libdir}" != "/usr/lib" ]; then
        mv ${D}/usr/lib ${D}${libdir}
    fi
}

### package ###

FILES:${PN} = "${bindir} ${libdir} ${datadir}"

### ###

BBCLASSEXTEND = "native"

