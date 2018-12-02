# /bin/python3


class Car:

    def __init__(self, plate, make, model):
        self.plate = plate
        self.make = make
        self.model = model
        return


class Parking_lot:

    def __init__(self, size):
        self.n = size
        self.lot = dict()
        return

    def search(self, plate):
        return plate in self.lot

    def put(self, car):
        if len(self.lot) == self.n:
            raise("Parking Lot is full")
        elif self.search(car.plate):
            raise("Car already in lot")
        else:
            self.lot[car.plate] = car
        return

    def get(self, plate):
        if self.search(plate):
            return self.lot.pop(plate)
        else:
            raise('Car Plate not in Lot')


if __name__ == "__main__":
    my_car = Car("CCZV288", "Hyundai", "Elantra")
    # Size 30 parking lot
    lot = Parking_lot(30)
    assert not lot.search(my_car.plate)
    lot.put(my_car)
    assert lot.search(my_car.plate)
    plate = my_car.plate
    del my_car
    my_car = lot.get(plate)
    assert my_car.plate == plate
    print(("Car Model: {} Car Make: {} Car Plate {}"
          .format(my_car.model, my_car.make, my_car.plate)))
