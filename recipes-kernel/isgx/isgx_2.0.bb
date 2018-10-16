SUMMARY = "Recipes for sgx out-of-tree driver build"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://License.txt;md5=b54f8941f6087efb6be3deb0f1e617f7"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git;branch=sgx2 \
           file://0001-isgx-kernel-modules.patch \
          "
PV = "2.0"
SRCREV = "eb61a952df31e3eb5526418611ebd3fd8b1ede11"
S = "${WORKDIR}/git"
KERNEL_MODULE_AUTOLOAD += "isgx"
# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
