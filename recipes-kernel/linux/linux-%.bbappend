FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

## Enable sgx in the Linux kernel if sgx is included in DISTRO_FEATURES.
SGX_CONFIG_URI += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'sgx', ' file://sgx.cfg', '', d)} \
"

# Only append to source URI if a kernel recipe is being bbappend'ed.
# Note that there exists recipe names that start with "linux-",
# for example, linux-libc-headers, linux-firmware, etc.
SRC_URI_append = "${@d.getVar('SGX_CONFIG_URI') if bb.data.inherits_class('kernel', d) else ''}"
