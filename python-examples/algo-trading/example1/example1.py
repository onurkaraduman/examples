#!/bin/python3

# Description:

import matplotlib.pyplot as plt
import pandas as pd

plt.style.use('fivethirtyeight')


class DataSource:
    def __init__(self, file_path: str, name: str):
        self.source = pd.read_csv(file_path)
        self.name = name
        self.start_date = self.__get_start_date()
        self.end_date = self.__get_end_date()

    def get_source(self):
        return self.source

    def __get_start_date(self):
        return self.source['Date'][0]

    def __get_end_date(self):
        return self.source['Date'][self.source['Date'].size - 1]


class Algo:

    def __init__(self, data_source: DataSource):
        self.data_source = data_source

    def show(self, column_name):
        sma30 = pd.DataFrame()
        sma100 = pd.DataFrame()
        sma30[column_name] = self.data_source.source[column_name].rolling(window=10).mean()
        sma100[column_name] = self.data_source.source[column_name].rolling(window=20).mean()
        plt.figure(figsize=(25.4, 4.5))
        plt.plot(self.data_source.source[column_name], label='AAPL')
        plt.plot(sma30[column_name], label='SMA10')
        plt.plot(sma100[column_name], label='SMA20')
        plt.title(self.data_source.name + ' Adj. Close Price History')
        plt.xlabel(self.data_source.start_date + ' - ' + self.data_source.end_date)
        plt.ylabel(column_name)
        plt.legend(loc='upper left')
        plt.show()


def main():
    data_source = DataSource('AAPL-3M.csv', 'AAPL')
    algo = Algo(data_source)
    algo.show('Adj Close')


if __name__ == '__main__':
    main()
