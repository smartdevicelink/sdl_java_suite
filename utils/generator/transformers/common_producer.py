"""
Common transformation
"""

import logging
import re
from abc import ABC
from collections import namedtuple

from model.array import Array
from model.enum import Enum
from model.struct import Struct


class InterfaceProducerCommon(ABC):
    """
    Common transformation
    """

    version = '1.0.0'

    def __init__(self, container_name, enums_package, structs_package, package_name,
                 enum_names=(), struct_names=(), key_words=()):
        self.logger = logging.getLogger('Generator.InterfaceProducerCommon')
        self.container_name = container_name
        self.enum_names = enum_names
        self.struct_names = struct_names
        self.key_words = key_words
        self.enums_package = enums_package
        self.structs_package = structs_package
        self.package_name = package_name
        self._params = namedtuple('params', 'deprecated description key last mandatory origin return_type since title '
                                            'param_doc name')

    @property
    def get_version(self):
        return self.version

    @property
    def params(self):
        """
        :return: namedtuple params(name='', origin='')
        """
        return self._params

    @staticmethod
    def key(param: str):
        """
        Convert param string to uppercase and inserting underscores
        :param param: camel case string
        :return: string in uppercase with underscores
        """
        if re.match(r'^[A-Z_\d]+$', param):
            return param
        else:
            return re.sub(r'([a-z]|[A-Z]{2,})([A-Z]|\d$)', r'\1_\2', param).upper()

    @staticmethod
    def ending_cutter(n: str):
        """
        If string not contains only uppercase letters and end with 'ID' deleting 'ID' from end of string
        :param n: string to evaluate and deleting 'ID' from end of string
        :return: if match cut string else original string
        """
        if re.match(r'^\w+[a-z]+([A-Z]{2,})?ID$', n):
            return n[:-2]
        else:
            return n

    @staticmethod
    def extract_description(d):
        """
        Evaluate, align and delete @TODO
        :param d: list with description
        :return: evaluated string
        """
        return re.sub(r'(\s{2,}|\n|\[@TODO.+)', ' ', ''.join(d)).strip() if d else ''

    @staticmethod
    def replace_sync(name):
        """
        :param name: string with item name
        :return: string with replaced 'sync' to 'Sdl'
        """
        if name:
            return re.sub(r'^([sS])ync(.+)$', r'\1dl\2', name)
        return name

    def replace_keywords(self, name: str = '') -> str:
        """
        if :param name in self.key_words, :return: name += 'Param'
        :param name: string with item name
        """
        if name.casefold() in self.key_words:
            origin = name
            if name.isupper():
                name += '_PARAM'
            else:
                name += 'Param'
            self.logger.debug('Replacing %s with %s', origin, name)
        return self.replace_sync(name)

    def extract_type(self, param):
        """
        Evaluate and extract type
        :param param: sub-element Param of element from initial Model
        :return: string with sub-element type
        """

        def evaluate(t1):
            if isinstance(t1, Struct) or isinstance(t1, Enum):
                name = t1.name
                return name
            else:
                return type(t1).__name__

        if isinstance(param.param_type, Array):
            return 'List<{}>'.format(evaluate(param.param_type.element_type))
        else:
            return evaluate(param.param_type)
