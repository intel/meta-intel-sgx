FILES_${PN} += "${@base_conditional('USE_LDCONFIG', '1', '${sysconfdir}/ld.so.conf.d', '', d)}"


do_install_append () {
	if [ "${USE_LDCONFIG}" = "1" ]; then
		mkdir -p ${D}${sysconfdir}/ld.so.conf.d
                echo "include /etc/ld.so.conf.d/*.conf" >  ${D}${sysconfdir}/ld.so.conf
	fi
}
