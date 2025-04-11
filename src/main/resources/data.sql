INSERT INTO users (id,username,password, age, sex, phone_number, role,date_joined) VALUES (1,'FirstSeller','pbkdf2_sha256$870000$KSMStVMrKrAUpNdvou6NNk$Ogn+397mXhy1z0/pJ1LpAoMdR6x+0WEdr+Oik03uhfA=', 25, 'Male', '0121234567', 'Seller',now()) ;
-- Insert statements for inventory items
-- Insert statements for inventory items
INSERT INTO inventories (id, inventory_id, title, name, description, brand, category, color, material, hot_sales_score, weight, rating, price, quantity, width, height, length, discount, image_url, status, seller) VALUES
(1, 'FT001', 'Classic Leather Sofa', 'Comfort Plus Sofa', 'Elegant 3-seater leather sofa with premium cushioning', 'HomeComfort', 'furniture', '{brown, black, beige}', '{genuine leather, hardwood, high-density foam}', 85.00, 85.50, 4.80, 1299.99, 15, 95, 85, 220, 10.00, 'furniture', 'in_stock', 1),
(2, 'EL002', 'Smart 4K TV', 'UltraView 55', '55-inch 4K Smart LED TV with HDR', 'TechVision', 'electronics', '{black, silver}', '{aluminum, tempered glass, plastic}', 92.00, 18.50, 4.70, 799.99, 25, 8.5, 71.5, 123.5, 15.00, 'electronics', 'in_stock', 1),
(3, 'AP003', 'Cotton T-Shirt', 'Essential Crew Neck', 'Premium cotton crew neck t-shirt', 'StyleBasics', 'apparel', '{white, black, navy, grey, red}', '{100% cotton, organic cotton}', 78.00, 0.20, 4.50, 24.99, 200, 50, 1, 70, 0.00, 'apparel', 'in_stock', 1),
(4, 'KW004', 'Professional Chef Knife', 'Master Chef Knife Set', 'Professional-grade stainless steel knife set', 'KitchenPro', 'kitchenware', '{silver, black}', '{stainless steel, carbon steel, pakkawood}', 88.50, 1.20, 4.90, 129.99, 50, 5, 2.5, 35, 5.00, 'kitchenware', 'in_stock', 1),
(5, 'HD005', 'Decorative Wall Mirror', 'Vintage Round Mirror', 'Elegant round wall mirror with gold frame', 'HomeDecor', 'homedecor', '{gold, silver}', '{glass, metal alloy, brass coating}', 75.50, 3.50, 4.60, 89.99, 30, 60, 3, 60, 0.00, 'homedecor', 'in_stock', 1),
(6, 'BK006', 'Wireless Bookshelf Speakers', 'SoundMax Pro', 'High-fidelity wireless bookshelf speakers with Bluetooth', 'AudioTech', 'electronics', '{black, walnut, white}', '{MDF wood, aluminum, fabric mesh}', 82.50, 4.80, 4.70, 299.99, 40, 18, 30, 25, 0.00, 'electronics', 'in_stock', 1),
(7, 'OD007', 'Garden Lounge Set', 'Outdoor Paradise Set', '4-piece weather-resistant garden furniture set', 'OutdoorLiving', 'outdoor', '{grey, beige}', '{wicker, aluminum frame, polyester, water-resistant fabric}', 79.00, 45.00, 4.60, 899.99, 10, 180, 75, 250, 15.00, 'outdoor', 'low_stock', 1),
(8, 'LT008', 'Modern Floor Lamp', 'ArtDeco Light', 'Contemporary LED floor lamp with adjustable brightness', 'LightCraft', 'lighting', '{brass, matte black, chrome}', '{brass, steel, marble base, LED components}', 76.50, 5.20, 4.40, 199.99, 25, 30, 160, 30, 5.00, 'lighting', 'in_stock', 1),
(9, 'BG009', 'Leather Laptop Bag', 'Professional Messenger', 'Premium leather laptop bag with multiple compartments', 'BagMaster', 'accessories', '{brown, black, tan}', '{full-grain leather, brass hardware, cotton lining}', 88.00, 1.20, 4.80, 159.99, 45, 12, 30, 40, 0.00, 'accessories', 'in_stock', 1),
(10, 'KT010', 'Stand Mixer', 'ChefPro Mixer', 'Professional 5.5L stand mixer with multiple attachments', 'KitchenElite', 'kitchenware', '{red, black, silver, white}', '{die-cast aluminum, stainless steel, glass bowl}', 91.00, 10.50, 4.90, 449.99, 20, 22, 35, 35, 10.00, 'kitchenware', 'in_stock', 1),
(11, 'FT011', 'Gaming Chair', 'ProGamer Seat', 'Ergonomic gaming chair with lumbar support', 'GamePro', 'furniture', '{black/red, black/blue, white/black}', '{PU leather, steel frame, memory foam, mesh fabric}', 87.50, 22.50, 4.60, 249.99, 35, 65, 130, 70, 5.00, 'furniture', 'in_stock', 1),
(12, 'HD012', 'Smart Doorbell', 'SecureView Pro', 'WiFi-enabled video doorbell with motion detection', 'SmartHome', 'electronics', '{silver, bronze, black}', '{aluminum, tempered glass, weather-resistant plastic}', 89.00, 0.30, 4.70, 179.99, 50, 6, 2.5, 12, 0.00, 'electronics', 'in_stock', 1),
(13, 'BT013', 'Yoga Mat', 'ZenFlex Pro', 'Non-slip professional yoga mat with carrying strap', 'FitLife', 'sports', '{purple, blue, green, grey}', '{TPE foam, natural rubber, microfiber surface}', 76.00, 1.80, 4.50, 39.99, 100, 61, 0.6, 183, 0.00, 'sports', 'in_stock', 1),
(14, 'KW014', 'Air Fryer', 'HealthyCook XL', 'Digital air fryer with 8 preset cooking modes', 'KitchenTech', 'kitchenware', '{black, stainless steel}', '{stainless steel, non-stick coating, tempered glass}', 93.50, 5.20, 4.80, 129.99, 30, 35, 40, 35, 15.00, 'kitchenware', 'in_stock', 1),
(15, 'AP015', 'Winter Jacket', 'Arctic Explorer', 'Waterproof insulated winter jacket with hood', 'OutdoorPro', 'apparel', '{navy, black, olive, burgundy}', '{polyester shell, down filling, fleece lining, waterproof membrane}', 85.50, 1.50, 4.70, 199.99, 40, 60, 5, 75, 20.00, 'apparel', 'in_stock', 1),
(16, 'HD016', 'Smart Plant Pot', 'GreenThumb Pro', 'Self-watering smart plant pot with soil sensors', 'SmartGarden', 'homedecor', '{white, terracotta, grey}', '{ceramic, plastic reservoir, electronic components}', 77.50, 1.20, 4.40, 49.99, 60, 20, 25, 20, 0.00, 'homedecor', 'in_stock', 1),
(17, 'BK017', 'Wireless Earbuds', 'SoundPods Pro', 'True wireless earbuds with noise cancellation', 'AudioTech', 'electronics', '{white, black, navy}', '{plastic, silicone tips, metal drivers}', 94.50, 0.15, 4.80, 159.99, 150, 3, 2, 5, 10.00, 'electronics', 'in_stock', 1),
(18, 'FT018', 'Storage Ottoman', 'FlexiStore Cube', 'Multifunctional storage ottoman with tufted top', 'HomeStyle', 'furniture', '{grey, beige, navy, brown}', '{linen fabric, MDF frame, foam padding}', 82.00, 8.50, 4.60, 79.99, 40, 45, 45, 45, 5.00, 'furniture', 'in_stock', 1),
(19, 'KW019', 'Coffee Maker', 'BrewMaster Elite', 'Programmable coffee maker with thermal carafe', 'KitchenPro', 'kitchenware', '{stainless steel, black}', '{stainless steel, glass, plastic components}', 88.50, 4.20, 4.70, 129.99, 35, 20, 35, 25, 15.00, 'kitchenware', 'in_stock', 1),
(20, 'AP020', 'Running Shoes', 'SpeedFlex Pro', 'Lightweight running shoes with responsive cushioning', 'SportFit', 'sports', '{black/red, grey/blue, white/green}', '{mesh upper, rubber sole, EVA midsole, synthetic overlays}', 91.00, 0.60, 4.80, 129.99, 80, 12, 15, 30, 0.00, 'sports', 'in_stock', 1),
(21, 'LT021', 'Pendant Light', 'Modern Chandelier', 'Contemporary hanging pendant light for dining room', 'LightCraft', 'lighting', '{gold, black, copper}', '{brass, glass shade, steel cable, LED components}', 79.50, 3.80, 4.60, 189.99, 25, 45, 120, 45, 0.00, 'lighting', 'in_stock', 1),
(22, 'HD022', 'Area Rug', 'Luxury Persian Style', 'Vintage-inspired area rug with intricate patterns', 'HomeDecor', 'homedecor', '{multicolor, blue/beige, red/gold}', '{wool blend, cotton backing, synthetic fibers}', 83.00, 6.50, 4.70, 299.99, 30, 200, 1.5, 300, 15.00, 'homedecor', 'in_stock', 1),
(23, 'OD023', 'BBQ Grill', 'GrillMaster Pro', 'Premium gas BBQ grill with side burner', 'OutdoorChef', 'outdoor', '{stainless steel, black}', '{stainless steel, cast iron grates, aluminum frame}', 86.50, 45.50, 4.80, 599.99, 15, 60, 115, 140, 10.00, 'outdoor', 'low_stock', 1),
(24, 'BG024', 'Travel Backpack', 'Adventure Pro 40L', 'Water-resistant travel backpack with laptop compartment', 'TravelGear', 'accessories', '{black, navy, grey}', '{ripstop nylon, YKK zippers, padded mesh}', 88.00, 1.20, 4.70, 129.99, 45, 35, 20, 55, 0.00, 'accessories', 'in_stock', 1),
(25, 'EL025', 'Smart Watch', 'FitTech Pro', 'Fitness tracking smartwatch with heart rate monitor', 'TechWear', 'electronics', '{black, silver, rose gold}', '{aluminum case, gorilla glass, silicone strap}', 92.50, 0.045, 4.60, 249.99, 100, 3.8, 1.1, 4.4, 5.00, 'electronics', 'in_stock', 1),
(26, 'FT026', 'Desk Chair', 'ErgoComfort Plus', 'Ergonomic office chair with adjustable lumbar support', 'OfficeStyle', 'furniture', '{black, grey, blue}', '{mesh fabric, aluminum base, polyurethane wheels, foam padding}', 87.00, 15.50, 4.70, 279.99, 40, 65, 120, 65, 10.00, 'furniture', 'in_stock', 1),
(27, 'KW027', 'Food Processor', 'ChefMate Pro', '1000W food processor with multiple attachments', 'KitchenElite', 'kitchenware', '{silver, black}', '{stainless steel blades, BPA-free plastic, glass bowl}', 84.50, 4.20, 4.60, 159.99, 30, 25, 42, 28, 15.00, 'kitchenware', 'in_stock', 1),
(28, 'AP028', 'Leather Wallet', 'Classic Bifold', 'Genuine leather wallet with RFID protection', 'LeatherCraft', 'accessories', '{brown, black, tan}', '{full-grain leather, RFID blocking layer, nylon lining}', 81.00, 0.15, 4.50, 49.99, 75, 9, 2, 11, 0.00, 'accessories', 'in_stock', 1),
(29, 'HD029', 'Wall Clock', 'Modern Minimalist', 'Silent wall clock with modern design', 'TimeStyle', 'homedecor', '{white, black, natural wood}', '{bamboo, glass face, metal hands, quartz movement}', 76.50, 1.20, 4.40, 39.99, 50, 30, 4, 30, 5.00, 'homedecor', 'in_stock', 1),
(30, 'SP030', 'Dumbbell Set', 'FitPro Adjustable', 'Adjustable dumbbell set with stand', 'FitnessPro', 'sports', '{black/chrome}', '{steel plates, chrome handles, plastic adjusters}', 89.00, 40.00, 4.80, 299.99, 25, 20, 20, 40, 0.00, 'sports', 'in_stock', 1),
(31, 'EL031', 'Robot Vacuum', 'SmartVac Pro', 'Smart robot vacuum with mapping technology', 'CleanTech', 'electronics', '{white, black}', '{ABS plastic, rubber wheels, LIDAR sensors, metal components}', 90.50, 3.50, 4.70, 399.99, 35, 35, 10, 35, 10.00, 'electronics', 'in_stock', 1),
(32, 'FT032', 'Bookshelf', 'Modern Display', '5-tier modern bookshelf with metal frame', 'HomeStyle', 'furniture', '{oak/black, walnut/gold, white/silver}', '{engineered wood, steel frame, powder coating}', 82.00, 18.50, 4.50, 159.99, 20, 30, 180, 80, 0.00, 'furniture', 'in_stock', 1),
(33, 'BT033', 'Resistance Bands', 'FitFlex Set', 'Set of 5 resistance bands with different strengths', 'FitLife', 'sports', '{multicolor}', '{natural latex, fabric carrying bag, foam handles}', 78.50, 0.50, 4.60, 29.99, 100, 15, 8, 25, 0.00, 'sports', 'in_stock', 1),
(34, 'KW034', 'Blender', 'PowerBlend Pro', 'High-speed blender with multiple settings', 'KitchenPro', 'kitchenware', '{silver, black, red}', '{stainless steel blades, glass jar, plastic base}', 86.50, 4.80, 4.70, 149.99, 40, 20, 40, 20, 5.00, 'kitchenware', 'in_stock', 1),
(35, 'HD035', 'Throw Pillows', 'Comfort Decor Set', 'Set of 4 decorative throw pillows', 'HomeDecor', 'homedecor', '{navy/gold, grey/silver, beige/white}', '{velvet cover, polyester filling, metallic thread}', 77.00, 2.00, 4.40, 49.99, 60, 45, 15, 45, 15.00, 'homedecor', 'in_stock', 1),
(36, 'AP036', 'Denim Jacket', 'Classic Trucker', 'Vintage-style denim jacket with metal buttons', 'DenimCo', 'apparel', '{blue, black, light wash}', '{cotton denim, brass hardware, polyester thread}', 85.50, 0.80, 4.60, 89.99, 45, 55, 3, 65, 0.00, 'apparel', 'in_stock', 1),
(37, 'LT037', 'Table Lamp', 'ArtDeco Touch', 'Touch-sensitive table lamp with USB port', 'LightCraft', 'lighting', '{brass/white, black/gold, copper/beige}', '{metal base, fabric shade, LED bulb, electronic components}', 79.50, 1.80, 4.50, 69.99, 55, 25, 45, 25, 10.00, 'lighting', 'in_stock', 1),
(38, 'OD038', 'Garden Planter', 'GreenSpace XL', 'Large outdoor planter with drainage system', 'GardenPro', 'outdoor', '{terracotta, charcoal, stone grey}', '{weather-resistant resin, drainage mesh, UV protection}', 73.00, 5.20, 4.40, 79.99, 30, 60, 50, 60, 5.00, 'outdoor', 'in_stock', 1),
(39, 'EL039', 'Portable Speaker', 'SoundMax Mini', 'Waterproof portable Bluetooth speaker', 'AudioTech', 'electronics', '{black, blue, red, grey}', '{aluminum grille, rubber coating, waterproof fabric}', 88.50, 0.35, 4.70, 79.99, 80, 7, 7, 18, 0.00, 'electronics', 'in_stock', 1),
(40, 'KW040', 'Cookware Set', 'ChefPro Collection', '10-piece non-stick cookware set', 'KitchenElite', 'kitchenware', '{black/steel, copper/black}', '{aluminum core, non-stick coating, tempered glass lids, stainless steel handles}', 91.00, 12.50, 4.80, 299.99, 25, 35, 25, 60, 15.00, 'kitchenware', 'in_stock', 1),
(41, 'FT041', 'Writing Desk', 'Modern Workspace', 'Contemporary writing desk with storage drawers', 'HomeStyle', 'furniture', '{white/oak, black/walnut, grey/maple}', '{engineered wood, metal legs, drawer slides, wood veneer}', 84.50, 25.50, 4.60, 249.99, 20, 60, 75, 120, 10.00, 'furniture', 'in_stock', 1),
(42, 'BG042', 'Camera Backpack', 'PhotoPro Carrier', 'Professional camera backpack with laptop compartment', 'GearPro', 'accessories', '{black, grey}', '{waterproof nylon, padded dividers, reinforced zippers}', 82.50, 1.80, 4.70, 129.99, 35, 30, 50, 45, 0.00, 'accessories', 'in_stock', 1),
(43, 'SP043', 'Tennis Racket', 'ProServe Elite', 'Professional tennis racket with carrying case', 'SportPro', 'sports', '{black/yellow, white/blue}', '{graphite frame, synthetic strings, comfort grip}', 76.50, 0.30, 4.50, 159.99, 40, 30, 3, 70, 5.00, 'sports', 'in_stock', 1),
(44, 'HD044', 'Artificial Plant', 'GreenLife Faux', 'Realistic artificial fiddle leaf fig tree', 'HomeDecor', 'homedecor', '{green}', '{silk leaves, plastic stems, decorative pot, natural wood}', 71.00, 2.50, 4.30, 89.99, 50, 40, 120, 40, 0.00, 'homedecor', 'in_stock', 1),
(45, 'KW045', 'Knife Block Set', 'ChefPro Collection', '15-piece professional knife set with block', 'KitchenElite', 'kitchenware', '{stainless/black, stainless/wood}', '{high-carbon steel, wooden block, ergonomic handles}', 89.50, 5.80, 4.80, 199.99, 30, 15, 35, 35, 10.00, 'kitchenware', 'in_stock', 1),
(46, 'EL046', 'Wireless Keyboard', 'TypePro Mechanical', 'Mechanical wireless keyboard with RGB lighting', 'TechGear', 'electronics', '{black, white, space grey}', '{aluminum frame, PBT keycaps, mechanical switches, LED lights}', 87.50, 0.95, 4.70, 129.99, 60, 14, 4, 44, 5.00, 'electronics', 'in_stock', 1),
(47, 'AP047', 'Wool Sweater', 'Comfort Knit Classic', 'Premium merino wool sweater with ribbed details', 'StyleCraft', 'apparel', '{navy, grey, burgundy, cream}', '{merino wool, nylon blend, elastic ribbing}', 83.00, 0.40, 4.60, 89.99, 75, 55, 2, 70, 0.00, 'apparel', 'in_stock', 1),
(48, 'OD048', 'Solar Lights', 'GardenGlow Path', 'Set of 6 solar-powered garden path lights', 'OutdoorPro', 'outdoor', '{black, bronze, silver}', '{stainless steel, solar panels, LED lights, plastic stakes}', 78.50, 2.40, 4.40, 59.99, 40, 15, 45, 15, 15.00, 'outdoor', 'in_stock', 1),
(49, 'FT049', 'Bar Stools', 'Modern Counter Set', 'Set of 2 adjustable height bar stools', 'HomeStyle', 'furniture', '{black/chrome, white/gold, grey/silver}', '{faux leather, chrome base, hydraulic lift, rubber feet}', 81.50, 8.50, 4.50, 159.99, 30, 40, 110, 40, 10.00, 'furniture', 'in_stock', 1),
(50, 'HD050', 'Wall Shelves', 'Floating Display', 'Set of 3 floating wall shelves', 'HomeDecor', 'homedecor', '{white, black, natural wood}', '{engineered wood, metal brackets, mounting hardware}', 76.00, 3.20, 4.40, 49.99, 55, 20, 5, 60, 0.00, 'homedecor', 'in_stock', 1);