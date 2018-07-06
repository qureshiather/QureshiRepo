# !/usr/bin/python3
# CTCI 3.4
# Create a Queue using 2 Stacks


class StackQueue:

    def __init__(self):
        self.inbox = []
        self.outbox = []

    def enqueue(self, item):
        self.inbox.append(item)

    def dequeue(self):
        # if outbox is Empty
        if not self.outbox:
            # while Inbox has Values, is not Empty
            while(self.inbox):
                # Pop inbox into Outbox
                self.outbox.append(self.inbox.pop())
        return self.outbox.pop()


if __name__ in '__main__':
    q = StackQueue()
    q.enqueue(5)
    q.enqueue(42)
    q.enqueue(512)
    q.enqueue(31)
    print(q.dequeue())
