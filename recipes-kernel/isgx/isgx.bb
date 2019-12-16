SUMMARY = "Intel(R) SGX out-of-tree driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://License.txt;md5=74de18a247355fbeb0da9795cf104cd9"

DEPENDS += " virtual/kernel"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git \
           file://isgx_Makefile_for_yocto_build.patch \
          "

SRCREV = "51c4821b8a4fa0cba69f532474a13c298c5b4037"

S = "${WORKDIR}/git"
