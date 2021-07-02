# Railway Pattern
This pattern is example implementation of a railway,
where you pass a list of step instances to the build_railroad function, and it returns a callable which when called, will run steps in order passed.
If there is an error in any steps, the railroad will halt execution, log out as an error the traceback and the data dictionary.

I have added optional __validate_data__ and __rollback methods__ to the RailStep abstract class. These callbacks will be called on certain conditions

- __validate_data__ will be called prior to calling the step in the railroad function
- __rollback__ will be called if there is an error in the step

Each step has a factory method __build__ and I have created an example stateless (ReadFile, PrintFile) and stateful steps (EmailStep). 
You can pass dependencies to the factory method (do this at the top level), or instantiate them inside the factory method.

A shortcoming of this approach is a __data__ dictionary is being shared across all steps, it is up to the client to make sure the dictionary has the correct keys and values prior to each step.

### Testing

#### Unit Tests

- Each step can be easily unit tested. 
- Stateless steps just need to test their run method with inputted data. 
- Stateful steps can be easily unit tested by providing dependencies directly to the init method. 
    - Be sure to create tests for the factory class method which will instantiate your step and call init with what it has instantiated

#### Integration Tests

- You can create railroads and run them with test data in your tests, just make sure to build each step so that they don't cause any unwanted side effects during testing (ie. mocking the email client so you don't send out emails)
- Note that if you instantiate dependencies in the factory method, you will have to monkey patch references to your dependencies in your integration tests.
    - If you instead pass your dependencies to your factory, then you can just pass in mock dependencies during testing

#### Example Execution

```
$ python railway_pattern.py

INFO:__main__:Executing RailRoad: [<__main__.ReadFileStep object at 0x7f7033782b80>, <__main__.PrintFileStep object at 0x7f70337924c0>]
INFO:__main__:Succesfully Called Step: ReadFileStep
This a a test file
INFO:__main__:Succesfully Called Step: PrintFileStep
INFO:__main__:Executing RailRoad: [<__main__.ReadFileStep object at 0x7f7033782b80>, <__main__.PrintFileStep object at 0x7f70337924c0>]
ERROR:__main__:Step ReadFileStep has errored
Traceback (most recent call last):
  File "/$$$$/$$$$/$$$$/QureshiRepo/patterns/railway/railway_pattern.py", line 45, in railroad
    step.validate_data(data)
  File "/$$$$/$$$$/$$$$/QureshiRepo/patterns/railway/railway_pattern.py", line 65, in validate_data
    assert 'file_path' in data
AssertionError
DEBUG:__main__:Current data contains: {}
```