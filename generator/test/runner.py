#!/usr/bin/env python3
"""
Main entry point to run all tests
"""
import sys
from pathlib import Path
from unittest import TestLoader, TestSuite, TextTestRunner

PATH = Path(__file__).absolute()

sys.path.append(PATH.parents[1].joinpath('rpc_spec/InterfaceParser').as_posix())
sys.path.append(PATH.parents[1].as_posix())

try:
    from test_enums import TestEnumsProducer
    from test_functions import TestFunctionsProducer
    from test_structs import TestStructsProducer
    from test_code_format_and_quality import CodeFormatAndQuality
except ImportError as message:
    print('{}. probably you did not initialize submodule'.format(message))
    sys.exit(1)


def main():
    """
    Main entry point to run all tests
    """

    suite = TestSuite()
    suite.addTests(TestLoader().loadTestsFromTestCase(TestFunctionsProducer))
    suite.addTests(TestLoader().loadTestsFromTestCase(TestEnumsProducer))
    suite.addTests(TestLoader().loadTestsFromTestCase(TestStructsProducer))
    suite.addTests(TestLoader().loadTestsFromTestCase(CodeFormatAndQuality))

    ret = not runner.run(suite).wasSuccessful()
    sys.exit(ret)


if __name__ == '__main__':
    main()
