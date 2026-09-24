-- Users Seed Data
INSERT OR REPLACE INTO users (id, avatar, username) VALUES
(1, 'avatar_1.png', 'alex_traveler'),
(2, 'avatar_2.png', 'sarah_adventures'),
(3, 'avatar_3.png', 'marco_polo');

-- Trips Seed Data
INSERT OR REPLACE INTO trips (id, userId, destination, duration) VALUES
('1', 1, 'Paris, France', '5 Days'),
('2', 1, 'Tokyo, Japan', '10 Days'),
('3', 1, 'Rome, Italy', '7 Days'),
('4', 1, 'New York, USA', '4 Days');

-- Details Seed Data
INSERT OR REPLACE INTO details (user, avatar, name, userSince, location) VALUES
('alex_traveler', 'avatar_1.png', 'Alex Johnson', '2021', 'Paris, France'),
('sarah_adventures', 'avatar_2.png', 'Sarah Connor', '2022', 'Tokyo, Japan'),
('marco_polo', 'avatar_3.png', 'Marco Polo', '2020', 'Rome, Italy');

-- Itinerary Items Seed Data
-- Trip 1: Paris
INSERT OR REPLACE INTO itinerary_items (id, tripId, dayNumber, dateText, stopLocation, timeRange) VALUES
('101', '1', 1, 'Oct 12 • Arrival & Eiffel Tower', 'Morning Flight & Hotel Check-in', '08:00 AM - 01:00 PM'),
('102', '1', 1, 'Oct 12 • Arrival & Eiffel Tower', 'Eiffel Tower Guided Walk & Photo Stop', '03:00 PM - 06:00 PM'),
('103', '1', 1, 'Oct 12 • Arrival & Eiffel Tower', 'Welcome Dinner at Seine River Bistro', '07:30 PM - 09:30 PM'),
('104', '1', 2, 'Oct 13 • Art & Culture', 'Louvre Museum Guided Tour', '09:30 AM - 01:00 PM'),
('105', '1', 2, 'Oct 13 • Art & Culture', 'Montmartre & Sacré-Cœur Stroll', '02:30 PM - 05:30 PM'),
('106', '1', 3, 'Oct 14 • Day Trip & Jazz Night', 'Palace of Versailles Excursion', '08:30 AM - 04:00 PM'),
('107', '1', 3, 'Oct 14 • Day Trip & Jazz Night', 'Evening Jazz Club in Le Marais', '08:00 PM - 10:30 PM');

-- Trip 2: Tokyo
INSERT OR REPLACE INTO itinerary_items (id, tripId, dayNumber, dateText, stopLocation, timeRange) VALUES
('201', '2', 1, 'Nov 02 • Shinjuku Arrival', 'Arrival at Narita & Shinjuku Check-in', '10:00 AM - 02:00 PM'),
('202', '2', 1, 'Nov 02 • Shinjuku Arrival', 'Omoide Yokocho Evening Food Tour', '06:00 PM - 09:00 PM'),
('203', '2', 2, 'Nov 03 • Historic Asakusa & Akihabara', 'Asakusa Senso-ji Temple & Nakamise St', '09:00 AM - 12:30 PM'),
('204', '2', 2, 'Nov 03 • Historic Asakusa & Akihabara', 'Akihabara Tech & Manga Exploration', '02:00 PM - 06:00 PM'),
('205', '2', 3, 'Nov 04 • Harajuku & Shibuya', 'Meiji Shrine & Harajuku Takeshita St', '10:00 AM - 01:30 PM'),
('206', '2', 3, 'Nov 04 • Harajuku & Shibuya', 'Shibuya Crossing & Rooftop View', '04:00 PM - 07:30 PM');

-- Trip 3: Rome
INSERT OR REPLACE INTO itinerary_items (id, tripId, dayNumber, dateText, stopLocation, timeRange) VALUES
('301', '3', 1, 'Dec 05 • Historic Center Arrival', 'Arrival & Trastevere Walk', '11:00 AM - 03:00 PM'),
('302', '3', 1, 'Dec 05 • Historic Center Arrival', 'Traditional Roman Pasta Dinner', '07:00 PM - 09:00 PM'),
('303', '3', 2, 'Dec 06 • Ancient Wonders', 'Colosseum & Roman Forum Tour', '09:00 AM - 01:00 PM'),
('304', '3', 2, 'Dec 06 • Ancient Wonders', 'Trevi Fountain & Pantheon Stroll', '03:00 PM - 06:00 PM');

-- Trip 4: New York
INSERT OR REPLACE INTO itinerary_items (id, tripId, dayNumber, dateText, stopLocation, timeRange) VALUES
('401', '4', 1, 'Jan 15 • Manhattan Arrival', 'Hotel Check-in & Times Square Walk', '01:00 PM - 04:00 PM'),
('402', '4', 1, 'Jan 15 • Manhattan Arrival', 'Broadway Evening Show', '07:00 PM - 10:00 PM'),
('403', '4', 2, 'Jan 16 • Central Park & Museums', 'Central Park Walk & MET Museum', '09:30 AM - 02:00 PM'),
('404', '4', 2, 'Jan 16 • Central Park & Museums', 'Empire State Building Night View', '06:30 PM - 08:30 PM');


-- Transit Stops Seed Data
-- Mock Transit route assigned to Trip 1 (Paris)
INSERT OR REPLACE INTO transit_stops (id, tripId, time, locationName, details, state) VALUES
('1', '1', '08:00 AM', 'Central Station', 'Platform 4 • Express Train', 'PASSED'),
('2', '1', '09:15 AM', 'Northwood Transfer', '5 min layover', 'PASSED'),
('3', '1', '10:30 AM', 'Mountain Pass', 'Scenic overlook stop', 'CURRENT'),
('4', '1', '11:45 AM', 'Valley Hub', 'Bus transfer required', 'UPCOMING'),
('5', '1', '01:00 PM', 'Coastal Terminus', 'Final Destination', 'UPCOMING');

-- Interest Places Seed Data
-- Map Explore Locations assigned to Trip 1 (Paris)
INSERT OR REPLACE INTO interest_places (id, tripId, name, foodType, rating, pricePoint, tags, offsetX, offsetY) VALUES
('1', '1', 'Le Bistrot Gourmand', 'Traditional French', 4.8, '€€€', 'Recommended, Romantic, Outdoor Seating', 60, 120),
('2', '1', 'Sushi Kyoto Star', 'Authentic Japanese', 4.9, '€€€€', 'Top Rated, Fresh Fish, Chef''s Menu', 180, 260),
('3', '1', 'Pizzeria Roma Bella', 'Classic Italian Pizza', 4.6, '€€', 'Family Friendly, Wood Oven, Fast Service', 120, 420);

-- Map Explore Locations assigned to Trip 2 (Tokyo)
INSERT OR REPLACE INTO interest_places (id, tripId, name, foodType, rating, pricePoint, tags, offsetX, offsetY) VALUES
('4', '2', 'Ramen Ichiran', 'Ramen', 4.7, '¥¥', 'Quick Bite, Solo Dining, Hot & Spicy', 100, 150),
('5', '2', 'Omoide Yokocho BBQ', 'Yakitori', 4.6, '¥¥', 'Street Food, Local Vibes, Drinks', 200, 300);

-- Map Explore Locations assigned to Trip 3 (Rome)
INSERT OR REPLACE INTO interest_places (id, tripId, name, foodType, rating, pricePoint, tags, offsetX, offsetY) VALUES
('6', '3', 'Osteria Da Fortunata', 'Roman Pasta', 4.8, '€€', 'Fresh Pasta, Authentic, Central', 150, 200),
('7', '3', 'Giolitti', 'Gelato', 4.7, '€', 'Dessert, Historic, Popular', 250, 350);

-- Pack Items Seed Data
INSERT OR REPLACE INTO pack_items (id, name, type, category, baseQuantityPerDay, isPacked) VALUES
('1', 'Underwear', 'PerDay', 'Clothing', 1, 0),
('2', 'Socks', 'PerDay', 'Clothing', 1, 0),
('3', 'Undershirts', 'PerDay', 'Clothing', 1, 0),
('4', 'T-Shirts', 'PerDay', 'Clothing', 1, 0),
('5', 'Pants', 'Fixed', 'Clothing', 2, 0),
('6', 'Hoodie', 'Fixed', 'Cold Weather', 1, 0),
('7', 'Coat', 'Fixed', 'Cold Weather', 1, 0),
('8', 'Gloves', 'Fixed', 'Cold Weather', 1, 0),
('9', 'Boots', 'Fixed', 'Cold Weather', 1, 0),
('10', 'Weather Pants', 'Fixed', 'All Weather', 1, 0),
('11', 'Weather Shirts', 'Fixed', 'All Weather', 2, 0),
('12', 'Shorts', 'Fixed', 'All Weather', 2, 0),
('13', 'Soap', 'Fixed', 'Toiletries', 1, 0),
('14', 'Shampoo', 'Fixed', 'Toiletries', 1, 0),
('15', 'Toothbrush / Floss / Toothpaste', 'Fixed', 'Toiletries', 1, 0),
('16', 'Lotions', 'Fixed', 'Toiletries', 1, 0),
('17', 'Cleaning Wipes', 'Fixed', 'Toiletries', 1, 0),
('18', 'Sunscreen', 'Fixed', 'Toiletries', 1, 0),
('19', 'GI Medicine', 'Fixed', 'Medicine', 1, 0),
('20', 'Allergy Meds', 'Fixed', 'Medicine', 1, 0),
('21', 'Headache Meds', 'Fixed', 'Medicine', 1, 0),
('22', 'Fiber Supplement', 'Fixed', 'Medicine', 1, 0),
('23', 'Daily Prescription Meds', 'Fixed', 'Medicine', 1, 0),
('24', 'Laptop', 'Fixed', 'Electronics', 1, 0),
('25', 'Phone', 'Fixed', 'Electronics', 1, 0),
('26', 'Headphones', 'Fixed', 'Electronics', 1, 0),
('27', 'Chargers & Cables (USB A->Micro B, C->C)', 'Fixed', 'Electronics', 1, 0),
('28', 'Power Strip', 'Fixed', 'Electronics', 1, 0),
('29', 'Gaming Device', 'Fixed', 'Electronics', 1, 0),
('30', 'Battery Pack', 'Fixed', 'Electronics', 1, 0),
('31', 'Swimwear', 'Fixed', 'Swim Stuff', 1, 0),
('32', 'Umbrella / Rain Protection', 'Fixed', 'Rain Protection', 1, 0),
('33', 'House & Car Keys', 'Fixed', 'Essentials', 1, 0),
('34', 'ID / Passport', 'Fixed', 'Essentials', 1, 0),
('35', 'Money / Credit Cards / Coin Holder', 'Fixed', 'Essentials', 1, 0),
('36', 'Sunglasses', 'Fixed', 'Essentials', 1, 0),
('37', 'Snacks, Reusable Ziplocks, Tea & Water', 'Fixed', 'Food & Drink', 1, 0),
('38', 'Extra Bags & Food Containers', 'Fixed', 'Food & Drink', 1, 0),
('39', 'Presentation Remote', 'Fixed', 'Giving Talk', 1, 0),
('40', 'Beach Towel & Swim Bag', 'Fixed', 'Beach', 1, 0),
('41', 'International Power Adapter', 'Fixed', 'International Travel', 1, 0),
('42', 'Load Work & Entertainment', 'Checklist', 'Night Before', 1, 0),
('43', 'Charge All Electronics', 'Checklist', 'Night Before', 1, 0),
('44', 'Prep Travel Snacks', 'Checklist', 'Night Before', 1, 0),
('45', 'Download Movies, Music, Books', 'Checklist', 'Night Before', 1, 0),
('46', 'Bedroom: Shut off water & check temp', 'Checklist', 'Leaving House Checklist', 1, 0),
('47', 'Den: Lock door', 'Checklist', 'Leaving House Checklist', 1, 0),
('48', 'Cats: Clean litter box, fill food & water', 'Checklist', 'Leaving House Checklist', 1, 0),
('49', 'Bathroom: Turn off water', 'Checklist', 'Leaving House Checklist', 1, 0),
('50', 'Kitchen: Shut off water/oven, fridge closed & clean', 'Checklist', 'Leaving House Checklist', 1, 0),
('51', 'Doors & Windows locked', 'Checklist', 'Leaving House Checklist', 1, 0),
('52', 'Set up dehumidifier', 'Checklist', 'Leaving House Checklist', 1, 0),
('53', 'Tea, Hot Water & Cold Water Tank', 'Fixed', 'Baby', 1, 0),
('54', 'Baby Water Bottles & Snacks', 'Fixed', 'Baby', 1, 0),
('55', 'Crib & Crib Sheet', 'Fixed', 'Baby', 1, 0),
('56', 'Diapers (8 per day)', 'PerDay', 'Baby', 8, 0),
('57', 'Diaper Bag (Wipes, Swaddles, Clothes, Creams)', 'Fixed', 'Baby', 1, 0),
('58', 'Pacifiers & Shusher', 'Fixed', 'Baby', 1, 0),
('59', 'Food Supply (Pumps, Bottles, Cooler Bags, Ice Blocks)', 'Fixed', 'Baby', 1, 0),
('60', 'Harness & Laundry Bags', 'Fixed', 'Baby', 1, 0),
('61', 'Clean Baby Pack (Bibs, Muslin, Burp Cloths)', 'Fixed', 'Baby', 1, 0),
('62', 'Picnic Water', 'Fixed', 'Picnic', 1, 0),
('63', 'Plates, Bowls, Utensils & Paper Towels', 'Fixed', 'Picnic', 1, 0),
('64', 'Picnic Blanket & Plastic Blanket', 'Fixed', 'Picnic', 1, 0),
('65', 'Cheese (Soft & Hard), Bread, Crackers', 'Fixed', 'Picnic', 1, 0),
('66', 'Nuts, Dried Fruit, Jams', 'Fixed', 'Picnic', 1, 0),
('67', 'Sandwiches, Salads, Meze Dips', 'Fixed', 'Picnic', 1, 0);
