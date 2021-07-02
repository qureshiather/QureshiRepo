#!/usr/bin/env python3

from abc import ABC, abstractmethod
from typing import Callable, List
import logging

logging.basicConfig()
logger= logging.getLogger( __name__ )
logger.setLevel(logging.DEBUG)

class RailStep(ABC):
    """
    Each step of the RailRoad must implement the run method, assume
    the data dictionary being passed has the correct information
    """
    
    @classmethod
    @abstractmethod
    def build() -> 'RailStep':
        pass

    def validate_data(self, data: dict):
        """
        Optional method to validate data dictionary
        """
        pass
    
    @abstractmethod
    def run(self, data: dict):
        pass

    def rollback(self, data: dict, error: Exception):
        """
        Optional method to rollback any state changes that may have occured, exception is passed
        that was passed in the run method above
        """
        pass

def build_railroad(steps: List[RailStep]) -> Callable:
    
    def railroad(data: dict) -> None:
        logger.info(f'Executing RailRoad: {steps}')
        for step in steps:
            try:
                step.validate_data(data)
                step.run(data)
            except Exception as err:
                logger.error(f"Step {step.__class__.__name__} has errored", exc_info=err)
                logger.debug(f'Current data contains: {data}')
                step.rollback(data, error=err)
                break
            else:
                logger.info(f"Succesfully Called Step: {step.__class__.__name__}")

    return railroad


class ReadFileStep(RailStep):

    @classmethod
    def build(cls) -> 'ReadFileStep':
        return cls()

    def validate_data(self, data: dict):
        assert 'file_path' in data

    def run(self, data: dict):
        with open(data.get('file_path'), "r") as f:
            contents = f.read()
        data.update(contents=contents)


class PrintFileStep(RailStep):

    @classmethod
    def build(cls) -> 'PrintFileStep':
        return cls()

    def validate_data(self, data: dict):
        assert 'contents' in data

    def run(self, data: dict):
        print(data.get('contents'))

class SendEmailStep(RailStep):

    @classmethod
    def build(cls) -> 'SendEmailStep':
        # Instantiate any state here
        # if you want to pass state to factory, you can add an argument here
        email_client = FakeEmailClient()
        return cls(email_client)

    def __init__(self, email_client):
        self.email_client = email_client

    def validate_data(self, data: dict):
        assert 'email_address' in data
        assert 'email_body' in data

    def run(self, data: dict):
        self.email_client.send_email(email_address=data.get('email_address'), body=data.get('email_body'))


class FakeEmailClient:

    def __init__(self):
        pass

    def send_email(self, email_address: str, body: str):
        print(f"Sending Email: {email_address} with body: {body}")


if __name__ in '__main__':
    rail_road = build_railroad(steps=[ReadFileStep.build(), PrintFileStep.build()])
    rail_road(data={'file_path': 'test.txt'})
    rail_road(data={})