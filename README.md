This README file contains information on the contents of the
meta-intel-sgx layer to support Intel(R) SGX on Yocto.


Dependencies
============

1) meta-oe for protobuf recipe
URI: git://git.openembedded.org/meta-openembedded -b sumo

2) systemd initialization manager
URI: https://www.yoctoproject.org/docs/current/mega-manual/mega-manual.html#selecting-an-initialization-manager

Besides dependencies for SGX support on Yocto, Intel(R) SGX technology
needs to be supported by both the silicon as well as the boot firmware,
namely, UEFI BIOS, Slimboot bootloader, etc.


Patches
=======

Please submit any patches via Github pull requests.

For discussion or patch submission via email, use the following:
meta-intel@lists.yoctoproject.org

Furthermore, in that email, make sure to copy the maintainer and add
"[meta-intel-sgx]" prefix to the subject.

Maintainer: Naveen R. Iyer <naveen.iyer@intel.com>


Adding the meta-intel-sgx layer to your Yocto build
===================================================

  1) bblayers.conf
  2) local.conf


bblayers.conf
=============

Add the location of the meta-intel-sgx layer to bblayers.conf, in
order to make the build system aware of it, along with any other layers
needed, for example:

  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-yocto \
    /path/to/yocto/meta-yocto-bsp \
    /path/to/yocto/meta-openembedded/meta-oe \
    /path/to/yocto/meta-intel-sgx \
    "


local.conf
==========

Add the following to local.conf in order to enable Intel(R) SGX without
the SGX SDK:

  # Enable Intel(R) SGX support.
  IMAGE_INSTALL_append = " sgx"

Add the following to local.conf in order to enable Intel(R) SGX,
including SGX SDK:
  # Enable Intel(R) SGX with SDK support.
  IMAGE_INSTALL_append = " sgx-dev"

Depending on whether the processor and the boot firmware supports SGX
Launch Control Configuration (LCC) (or in other words, Flex Launch
Control (FLC)), you can pick one of the three SGX LCC modes in the
boot firmware, namely, Unlocked Mode, Intel Locked Mode, OEM/3rd Party
Locked Mode.

If you choose to pick any of the Locked Modes, then SGX
out-of-tree ('isgx') driver must be used. To use the SGX out-of-tree
driver, add the following line to local.conf:
IMAGE_INSTALL_append = " isgx"

On the other hand, if you choose to pick UnLocked Mode, then SGX
in-kernel driver must be used. To use the SGX in-kernel driver,
add the following line to local.conf:
DISTRO_FEATURES_append = " sgx"


Changes to local.conf and sgx.cfg
=================================

Depending on whether the processor and the boot firmware supports SGX
Launch Control Configuration (LCC) (or in other words, Flex Launch
Control (FLC)), you can pick one of the three SGX LCC modes in the
boot firmware, namely, Unlocked Mode, Intel Locked Mode, OEM/3rd Party
Locked Mode.

In order to use any of the Locked Modes, wherein, IA32_SGXLEPUBKEYHASH
MSRs are programmed before booting to OS, then SGX out-of-tree ('isgx')
driver must be used. There are two steps to do that:
Step 1/2) Add "isgx" to IMAGE_INSTALL in local.conf:
IMAGE_INSTALL_append = " isgx"
Step 2/2) Set SGX kernel configs in recipes-kernel/linux/files/sgx.cfg to
"n":
CONFIG_INTEL_SGX_CORE=n
CONFIG_INTEL_SGX=n

On the other hand, if you choose to pick UnLocked Mode, then SGX
in-kernel driver must be used. To use the SGX in-kernel driver,
leave the sgx.cfg as is (set to "y") and add the following line to
local.conf:
DISTRO_FEATURES_append = " sgx"


Copying
=======

Unless noted otherwise, files are provided under the MIT license (see COPYING.MIT)
and are Copyright © Intel Corporation 2018.

