#!/usr/bin/env bash
#
# Copyright (C) 2011-2018 Intel Corporation. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
#
#   * Redistributions of source code must retain the above copyright
#     notice, this list of conditions and the following disclaimer.
#   * Redistributions in binary form must reproduce the above copyright
#     notice, this list of conditions and the following disclaimer in
#     the documentation and/or other materials provided with the
#     distribution.
#   * Neither the name of Intel Corporation nor the names of its
#     contributors may be used to endorse or promote products derived
#     from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
#


if test $(id -u) -ne 0; then
    echo "Root privilege is required."
    exit 1
fi

get_lib()
{
    echo "$(basename $(gcc -print-multi-os-directory))"
}

# Killing AESM service
/usr/sbin/service aesmd stop
systemctl disable aesmd
# Removing AESM configuration files
rm -f /lib/systemd/system/aesmd.service
rm -f /etc/aesmd.conf

# Removing AESM internal folders
#rm -fr /var/opt/aesmd
rm -fr /var/run/aesmd

# Removing runtime libraries
rm -f /usr/$(get_lib)/libsgx_uae_service.so
rm -f /usr/$(get_lib)/libsgx_urts.so
rm -f /usr/$(get_lib)/libsgx_enclave_common.so*
rm -f /usr/lib/i386-linux-gnu/libsgx_uae_service.so
rm -f /usr/lib/i386-linux-gnu/libsgx_urts.so
rm -f /usr/lib/i386-linux-gnu/libsgx_enclave_common.so*

# Removing AESM user and group
/usr/sbin/userdel aesmd 2> /dev/null
/usr/sbin/groupdel aesmd 2> /dev/null

# Removing AESM folder
rm -fr /opt/intel/sgxpsw

echo "Intel(R) SGX PSW uninstalled."
