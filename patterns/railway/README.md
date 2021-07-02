#### Railway Pattern
This pattern is example implementation of a railway,
where you pass a list of step instances to the build_railroad function, and it returns a callable which when called, will run steps in order passed

I have added optional __validate_data__ and __rollback methods__ to the RailStep abstract class. These callbacks will be called on certain conditions

- __validate_data__ will be called prior to calling the step in the railroad function
- __rollback__ will be called if there is an error in the step

Each step has a factory method __build__ and I have created an example stateless (ReadFile, PrintFile) and stateful steps (EmailStep).