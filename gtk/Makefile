#
# a general Makefile to build gtk app in only one src file case.
#
# https://www.cnblogs.com/gylei/p/3196887.html
#


CC       = gcc
CFLAGS   =  
LDFLAGS  =  `pkg-config gtk+-2.0 --cflags --libs`

SRCS = $(wildcard *.c)
TARGETS = $(patsubst %.c, %, $(SRCS))

all:$(TARGETS)

$(TARGETS):$(SRCS)
	$(CC) $^ -o $@ $(LDFLAGS) $(CFLAGS)

clean:
	-rm -rf $(TARGETS) *~ .*swp

.PHONY: clean all

