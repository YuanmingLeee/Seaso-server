#!/usr/bin/env bash
case $1 in
    'tracker')
        echo 'configure tracker only'
        cp config/storage.conf /etc/fdfs;;
    'storage')
        echo 'configure storage only'
        cp config/tracker.conf /etc/fdfs;;
    '')
        echo 'configure both'
        cp config/storage.conf /etc/fdfs
        cp config/tracker.conf /etc/fdfs;;
    *)
        echo 'Wrong argument is provided. Expecting one of the following: tracker, storage, <empty>';;
esac
echo 'finish configuration'
