SUMMARY = "Intel(R) SGX out-of-tree driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://License.txt;md5=b54f8941f6087efb6be3deb0f1e617f7"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git \
           file://0001-isgx-kernel-modules.patch \
          "

SRCREV = "2a509c203533f9950fa3459fe91864051bc021a2"
S = "${WORKDIR}/git"
KERNEL_MODULE_AUTOLOAD += "isgx"
# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
