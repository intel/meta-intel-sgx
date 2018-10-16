FILESEXTRAPATHS_prepend := "${THISDIR}/linux-yocto:"

# Enable kernel support if the feature is available on the machine.
SRC_URI += " \
    ${@base_contains('MACHINE_FEATURES', 'sgx', 'file://sgx.scc', '', d)} \
    ${@base_contains('MACHINE_FEATURES', 'sgx', 'file://sgx.cfg', '', d)} \    
"

