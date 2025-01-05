db = db.getSiblingDB('product-db');

db.products.insertMany([
	{ "code": 10, "name": "Asus", "description": "Gaming laptop", "price": 2000 },
	{ "code": 20, "name": "Acer", "description": "Gaming laptop", "price": 1800 },
	{ "code": 30, "name": "Dell", "description": "Gaming laptop", "price": 2200 },
	{ "code": 40, "name": "Mac", "description": "Gaming laptop", "price": 4000 },
	{ "code": 100, "name": "Fujitsu", "description": "Gaming desktop", "price": 3000 },
	{ "code": 110, "name": "Acer", "description": "Gaming desktop", "price": 2800 },
	{ "code": 120, "name": "Toshiba", "description": "Gaming desktop", "price": 3200 },
	{ "code": 200, "name": "Samsung", "description": "Gaming monitor", "price": 500 },
	{ "code": 210, "name": "Dell", "description": "Gaming monitor", "price": 600 }
]);
