SUMMARY = "Intel(R) SGX out-of-tree driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://License.txt;md5=74de18a247355fbeb0da9795cf104cd9"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git \
           file://0001-isgx-kernel-modules.patch \
          "

SRCREV = "0b76a7c905b8293ef18414bd3a3a867059a1ceb6"
S = "${WORKDIR}/git"
KERNEL_MODULE_AUTOLOAD += "isgx"
# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
