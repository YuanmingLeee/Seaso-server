#!/usr/bin/env bash
case $1 in
    'tracker')
        echo 'configure tracker only'
        cp bin/config/storage.conf /etc/fdfs;;
    'storage')
        echo 'configure storage only'
        cp bin/config/tracker.conf /etc/fdfs;;
    '')
        echo 'configure both'
        cp bin/config/storage.conf /etc/fdfs
        cp bin/config/tracker.conf /etc/fdfs;;
    *)
        echo 'Wrong argument is provided. Expecting one of the following: tracker, storage, <empty>';;
esac
echo 'finish configuration'
