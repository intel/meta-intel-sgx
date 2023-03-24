SUMMARY = "Intel(R) SGX Platform Software (PSW) and Software Development Kit (SDK)"
HOMEPAGE = "https://01.org/intel-softwareguard-extensions"

# TODO: Verify still valid
LICENSE = " \
  Intel-Redistributable-Binaries & \
  Intel-Sample-Source-Code & \
  MIT & \
  STLPort & \
  BSD-3-Clause & \
  EPL-1.0 \
"
LIC_FILES_CHKSUM = "file://License.txt;md5=4af23fbd783d5ab20d55ba56c0b7b894"

# Packages required to build
DEPENDS:append = " openssl-native cmake-native zlib"
DEPENDS:append:class-target = " openssl"

# Parallel make does not work with some recipes
PARALLEL_MAKE = ""
PARALLEL_MAKEINST = ""

# Source directory
S = "${WORKDIR}/git"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT  = "1"

# Build environment variables
export SYSROOT_INCDIR = "${STAGING_INCDIR}"
export SYSROOT_LIBDIR = "${STAGING_LIBDIR}"
export BUILD_SYS
export TARGET_SYS

# SGX environment variables
sgxdirprefix:class-native = "${RECIPE_SYSROOT_NATIVE}"
sgxdirprefix:class-target = ""
sgxrootdir = "/opt/intel"

sgxsdkbase = "sgxsdk"
sgxsdkdir  = "/${sgxsdkbase}"
sgxsdkpath = "${sgxrootdir}${sgxsdkdir}"

### source ###

require sgx-source_2.18.1.inc
