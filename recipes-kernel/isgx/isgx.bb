SUMMARY = "Intel(R) SGX out-of-tree driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://License.txt;md5=74de18a247355fbeb0da9795cf104cd9"

DEPENDS += " virtual/kernel"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git;branch=sgx2 \
           file://isgx_Makefile_for_yocto_build.patch \
          "

SRCREV = "2605efa4efb49130a1f4c3bc8d00125105a9c8a4"

S = "${WORKDIR}/git"

KERNEL_MODULE_AUTOLOAD += "isgx"
# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
