SUMMARY = "Intel(R) SGX out-of-tree driver"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://License.txt;md5=74de18a247355fbeb0da9795cf104cd9"

DEPENDS += " virtual/kernel"

inherit module

SRC_URI = "git://github.com/intel/linux-sgx-driver.git \
           file://isgx_Makefile_for_yocto_build.patch \
          "

SRCREV = "4505f07271ed82230fce55b8d0d820dbc7a27c5a"

S = "${WORKDIR}/git"

