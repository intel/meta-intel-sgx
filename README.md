# Intel(R) SGX Yocto Layer

This README file contains information on the contents of the
meta-intel-sgx layer to support Intel(R) SGX on Yocto.

## Dependencies

1) meta-oe for protobuf recipe
URI: git://git.openembedded.org/meta-openembedded -b sumo

2) systemd initialization manager
URI: https://www.yoctoproject.org/docs/current/mega-manual/mega-manual.html#selecting-an-initialization-manager

Besides dependencies for SGX support on Yocto, Intel(R) SGX technology
needs to be supported by both the silicon as well as the boot firmware,
namely, UEFI BIOS, Slimboot bootloader, etc.

## Patches

Please submit any patches via Github pull requests.

For discussion or patch submission via email, use the following:
meta-intel@lists.yoctoproject.org

Furthermore, in that email, make sure to copy the maintainer and add
"[meta-intel-sgx]" prefix to the subject.

Maintainers: Prakash, Chandra e-mail: prakash1.chandra@intel.com  
Adithya Nagaraj Baglody : e-mail: adithya.nagaraj.baglody@intel.com

## Adding the meta-intel-sgx layer to your Yocto build

  1) bblayers.conf
  2) local.conf

### bblayers.conf

Add the location of the meta-intel-sgx layer to bblayers.conf, in
order to make the build system aware of it, along with any other layers
needed, for example:

```
  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-yocto \
    /path/to/yocto/meta-yocto-bsp \
    /path/to/yocto/meta-openembedded/meta-oe \
    /path/to/yocto/meta-intel-sgx \
    "
```

### local.conf

Intel(R) SGX software stack for Linux includes the driver, PSW (Platform SW)
and SDK. Running Intel(R) SGX applications on target needs the driver and
PSW. Additionally, developing Intel(R) SGX applications on target will
need the SDK.

#### Step 1/2) Enable PSW and SDK (SDK is optional)

Add the following to local.conf in order to enable Intel(R) SGX PSW:

```
# Enable Intel(R) SGX PSW support.
IMAGE_INSTALL:append = " sgx"
```

Add the following to local.conf in order to enable Intel(R) SGX PSW and
SGX SDK:  

```
# Enable Intel(R) SGX PSW & SDK support.  
IMAGE_INSTALL:append = " sgx-dev"
```

#### Step 2/2) Enable the driver

Depending on whether the processor and the boot firmware supports SGX
Launch Control Configuration (LCC) (or in other words, Flex Launch
Control (FLC)), you can pick one of the three SGX LCC modes in the
boot firmware, namely, Unlocked Mode, Intel Locked Mode, OEM/3rd Party
Locked Mode.

If you choose to pick any of the Locked Modes, then SGX
out-of-tree ('isgx') driver must be used. To use the SGX out-of-tree
driver, add the following line to local.conf:  

```
IMAGE_INSTALL:append = " isgx"
```

On the other hand, if you choose to pick the Unlocked Mode, then SGX
in-kernel driver must be used. To use the in-kernel SGX driver, add the
following to local.conf:

```
DISTRO_FEATURES:append = " sgx"
```

## Copying

Unless noted otherwise, files are provided under the MIT license (see COPYING.MIT)
and are Copyright © Intel Corporation 2021.

