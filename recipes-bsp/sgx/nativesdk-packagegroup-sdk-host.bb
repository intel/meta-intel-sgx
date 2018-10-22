SUMMARY = "Host packages for the standalone SDK or external toolchain"
PR = "r0"
LICENSE = "BSD"

inherit packagegroup nativesdk

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS_${PN} = "\
    nativesdk-sgx \
    "
