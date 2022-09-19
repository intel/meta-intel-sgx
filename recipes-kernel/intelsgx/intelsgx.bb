SUMMARY = "Intel(R) SGX Data Center Attestation Primitives driver"
LICENSE = "BSD-3-Clause | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://License.txt;md5=633d1f5182ada7cd064194532a4a79d4"

DEPENDS += " virtual/kernel"

inherit module

SRC_URI  = "git://github.com/intel/SGXDataCenterAttestationPrimitives.git;protocol=https"
SRC_URI += "file://build_kernel_yocto.patch"

SRCREV = "98976322e8b58e23256355f5cf90b9e30e37d8c1"

S = "${WORKDIR}/git/driver/linux"

RPROVIDES:${PN} += "kernel-module-${PN}"

