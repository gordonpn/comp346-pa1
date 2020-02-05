The running times of the receiving client thread, the sending client thread, and the server thread are all different on all three test runs of the program.
The reason being is that thread interrupts are unpredictable and lead to different running times.
They are also dependent of the performance of a given computer and if the computer is experiencing unforeseen interrupts as well.
While a thread may be interrupted, the threads do not know the states of the buffers, there may be unfortunate cases where a thread resumes, and the buffer is not ready yet, which will cause a thread to yield again.