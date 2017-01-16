import os
import re
import csv
import json
import locale
#import collections

base_dir = "E:\Users\Guillaume\Downloads\movie_rating_prediction-master\movie_rating_prediction-master"
locale.setlocale( locale.LC_ALL, 'en_US.UTF-8' )


def parse_price(price):
    # eg: u'$237,000,000' --> 237000000
    if not price:
        return 0
    return locale.atoi(re.sub('[^0-9,]', "", price))

def find_estimated(line):
    if (line.find("(estimated)")==-1):
        return 0
    else:
        return 1

def load_unparsed_movie_metadata():
    try:
        with open(os.path.join(base_dir, "imdb_output.json"), "r") as f:
            movies = json.load(f)
            return movies
    except:
        print ("Cannot load the unparsed movie metadata file!")
        return None

def parse_one_movie_metadata(movie):
    if not movie:
        return None

    parsed_movie = {}

    parsed_movie['movie_title'] = movie['movie_title'].encode('utf-8')
    parsed_movie['budget'] = None if movie['budget'] is None or len(movie['budget']) == 0 else parse_price(movie['budget'][0].strip())
    parsed_movie['estimated']=None if movie['budget'] is None or len(movie['budget']) == 0 else find_estimated(movie['budget'])
    parsed_movie['title_year'] = None if movie['title_year'] is None else int(movie['title_year'])

    return parsed_movie

def parse_all_budget():
    movies = load_unparsed_movie_metadata()
    total_count = len(movies)
    print ("{} movie metadata were loaded!".format(total_count))

    with open("movie_budget.csv", "w") as f:
        header_was_written = False
        for i, movie in enumerate(movies):
            print ("Processing {} of {}: {}".format(i+1, total_count, movie['movie_imdb_link']))
            parsed_movie = parse_one_movie_metadata(movie)
            w = csv.DictWriter(f, parsed_movie.keys())
            if not header_was_written:
                w.writeheader()
                header_was_written = True

            w.writerow(parsed_movie)

parse_all_budget()
