SUMMARY = "Intel(R) SGX out-of-tree driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://License.txt;md5=74de18a247355fbeb0da9795cf104cd9"

DEPENDS += " virtual/kernel"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git;branch=sgx2 \
           file://isgx_Makefile_for_yocto_build.patch \
          "

SRCREV = "4f5bb63a99b785f03bb6a03dc5402e99691b849b"

S = "${WORKDIR}/git"
