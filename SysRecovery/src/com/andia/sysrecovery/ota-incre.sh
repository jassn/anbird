#!/bin/bash
#
#  Examples:
#    ./ota-incre.sh -i 20180329
#
#
#

check_arg(){
  if [ "x$P1" = "x" ]; then
     ARG="droid"
     LOG=${OUT_DIR}/BUILD-$ARG-$P2-`date +%Y%m%d-%H%M%S`.log
  elif [ "x$P1" = "xhelp" -o "x$P1" = "xh" ]; then
    cat <<HELP
    USAGE:
      1. cd Android_root
      2. make dist
      3. mv dist to desired path.
      4. edit this script - $P0.
HELP
    exit 1
  elif [ "x$P1" = "xmm" -o "x$P1" = "xm" -o "x$P1" = "xmmm" ]; then

    ARG=$P1
    MM_DIR=$P2
    PPP=`echo $P2 | sed "s/\//-/g"`
    LOG=${OUT_DIR}/BUILD-$ARG-$PPP-`date +%Y%m%d-%H%M%S`.log

  else
     # check value
     if [ "x$P1" = "xdroid" -o "x$P1" = "xsystemimage" -o "x$P1" = "xbootimage" -o "x$P1" = "xuboot" -o "x$P1" = "xotapackage" ]; then
       ARG=$P1
       LOG=${OUT_DIR}/BUILD-$ARG-$P2-`date +%Y%m%d-%H%M%S`.log
     else
       echo "command input error, please see help"
       echo "example: $0 help"
       exit 1
     fi

  fi
}


show_help(){
    cat <<HELP
    USAGE:
      1. cd Android_root
      2. make dist
      3. mv dist to desired path.
      4. edit this script - $P0.
HELP
}


export TOP=`pwd`
export OUT_DIR=out
mkdir -p $OUT_DIR

P0=$0
P1=$1
P2=$2
ARG=""
MM_DIR=""
LOG=""


###########################################

export COLOR_RESET='\e[0m' # No Color
export COLOR_WHITE='\e[1;37m'
export COLOR_BLACK='\e[0;30m'
export COLOR_BLUE='\e[0;34m'
export COLOR_LIGHT_BLUE='\e[1;34m'
export COLOR_GREEN='\e[0;32m'
export COLOR_LIGHT_GREEN='\e[1;32m'
export COLOR_CYAN='\e[0;36m'
export COLOR_LIGHT_CYAN='\e[1;36m'
export COLOR_RED='\e[0;31m'
export COLOR_LIGHT_RED='\e[1;31m'
export COLOR_PURPLE='\e[0;35m'
export COLOR_LIGHT_PURPLE='\e[1;35m'
export COLOR_BROWN='\e[0;33m'
export COLOR_YELLOW='\e[1;33m'
export COLOR_GRAY='\e[0;30m'
export COLOR_LIGHT_GRAY='\e[0;37m'

check_arg
# build $ARG $MM_DIR 
###########################################

#BASE_PATH=~/Music
BASE_PATH=~/Pictures

PRODUCT=sabresd_6dq

    
## PREV_NUM=20180319
if [ "x$PREV_NUM" = "x" ]; then
    PREV_NUM=20180324
fi

CURR_NUM=20180325

PREV_PATH=$BASE_PATH/dist-$PREV_NUM
CURR_PATH=$BASE_PATH/dist-$CURR_NUM

PREV_TARGET=$PREV_PATH/$PRODUCT-target_files-$PREV_NUM.zip
CURR_TARGET=$CURR_PATH/$PRODUCT-target_files-$CURR_NUM.zip
INCRE_OTA=$BASE_PATH/$PRODUCT-incre-$PREV_NUM-to-$CURR_NUM.zip
FULL_OTA=$BASE_PATH/$PRODUCT-ota-$CURR_NUM-dist.zip


###########################################
## Generate Incremental OTA package
###########################################
if [ -d $PREV_TARGET ]; then
	echo -e "PREV_TARGET does not exist!"
    exit 1
fi

echo -e "Old ...... $PREV_TARGET"
echo -e "New ...... $CURR_TARGET"
echo -e "${COLOR_GREEN}"
echo -e "Generating incremental OTA ...... ${INCRE_OTA}${COLOR_RESET}"

build/tools/releasetools/ota_from_target_files.py \
-i $PREV_TARGET $CURR_TARGET ${INCRE_OTA}

##
## Generate Full OTA package
##
#echo -e "${COLOR_GREEN}"
#echo -e "Generating Full OTA ...... ${FULL_OTA}${COLOR_RESET}"
#build/tools/releasetools/ota_from_target_files.py \
#$CURR_TARGET $FULL_OTA






