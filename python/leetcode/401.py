import itertools
from typing import List

class Solution:
    def readBinaryWatch(self, turnedOn: int) -> List[str]:
        #return self.read_binary_watch_init(turnedOn)
        #return self.read_binary_watch_enum(turnedOn)
        return self.read_binary_watch_enum_bit(turnedOn)

    def read_binary_watch_init(self, turned_on):
        """Generate combinations of possible lights selected from 4 options for hours and 16 options for minutes, creating bit masks to represent these selections and their numerical values, filtering for maximum 11 hours and 59 minutes, and generating cross products.
        """

        def binary_combos(n, k):
            masks = []
            indices = range(n)
            for combo in itertools.combinations(indices, k):
                mask = 0
                for index in combo:
                    mask |= (1 << index)
                masks.append(mask)
            return masks

        N_HOUR = 4
        N_MIN = 6
        MAX_HOUR = 11
        MAX_MIN = 59

        ans = []
        max_hour_on = min(turned_on, N_HOUR)
        for hour_on in range(max_hour_on + 1):
            if (turned_on - hour_on) <= N_MIN:
                hour_masks = [hour for hour in binary_combos(N_HOUR, hour_on) if hour <= MAX_HOUR]
                min_on = turned_on - hour_on
                min_masks = [m for m in binary_combos(N_MIN, min_on) if m <= MAX_MIN]
                for hour in hour_masks:
                    for minute in min_masks:
                        f_hour = str(hour)
                        f_minute = "{:0{}}".format(minute, 2)
                        ans.append(":".join([f_hour, f_minute]))
        return ans

    def read_binary_watch_enum(self, turned_on):
        """Iterate through all possible combinations of hours and minutes, counting the number of binary bits flipped in total and adding to the answer list if it matches the input turned on value.
        """
        ans = []
        for hour in range(12):
            for minute in range(60):
                if hour.bit_count() + minute.bit_count() == turned_on:
                    ans.append(":".join([str(hour), "{:0{}}".format(minute, 2)]))
        return ans

    def read_binary_watch_enum_bit(self, turned_on):
        """Iterate through all sequences of a 10 bit binary, isolating bits for hours and minutes, restricting below 12 hours and 60 minutes, and adding to the answer list if the total number of 1 bits matches the input turned on value.
        """
        ans = []
        for enum in range(2 ** 10):
            hour, minute = (enum >> 6, enum & 0b111111)
            if hour < 12 and minute < 60 and enum.bit_count() == turned_on:
                ans.append(":".join([str(hour), "{:0{}}".format(minute, 2)]))
        return ans