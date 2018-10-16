SUMMARY = "Intel iclsclient"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/iCLS_Client_License_Agreement.pdf;md5=1f6bfe5ac37580116006b14192991454 "

SRC_URI = "file://iclsClient-1.45.449.12-1.tar.gz"

SRC_URI_append = " file://iCLS_Client_License_Agreement.pdf"
SRC_URI_append_class-target = " file://iclsClient.conf"

#DEPENDS += "expat wayland mesa"
#RDEPENDS_${PN} += "bash libc libstdc++"

INSANE_SKIP_${PN} = "arch debug-files"

do_install() {
        install -d ${D}/opt/Intel/iclsClient/lib
        install -d ${D}/opt/Intel/iclsClient/log
        install -m 0755 ${WORKDIR}/opt/Intel/iclsClient/lib/libiclsclient.so ${D}/opt/Intel/iclsClient/lib
        install -m 0755 ${WORKDIR}/opt/Intel/iclsClient/lib/libiclsproxy.so ${D}/opt/Intel/iclsClient/lib
        cp  ${WORKDIR}/opt/Intel/iclsClient/* ${D}/opt/Intel/iclsClient/ || true

        install -d ${D}/${sysconfdir}/ld.so.conf.d/
        install -m 0644 ${WORKDIR}/iclsClient.conf ${D}/${sysconfdir}/ld.so.conf.d/
}

FILES_${PN} += "/opt"
