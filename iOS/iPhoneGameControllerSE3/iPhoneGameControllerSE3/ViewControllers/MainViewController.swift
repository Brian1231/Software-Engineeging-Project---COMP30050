//
//  ViewController.swift
//  iPhoneGameControllerSE3
//
//  Created by Conal O'Neill on 01/02/2018.
//  Copyright © 2018 Conal O'Neill. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
	
	var pictures = [String]()

	@IBOutlet weak var cardView: UIImageView!
	@IBOutlet weak var location: UILabel!
	@IBOutlet weak var bankBalanceInfo: UILabel!
	@IBOutlet weak var bankBalance: UILabel!
	@IBOutlet weak var numPropertiesInfo: UILabel!
	@IBOutlet weak var numProperties: UILabel!
	
	override func viewDidLoad() {
		super.viewDidLoad()
		
		let fm = FileManager.default
		let path = Bundle.main.resourcePath!
		let items = try! fm.contentsOfDirectory(atPath: path)
		
		for item in items {
			if item.hasPrefix("nssl") {
				// this is a picture to load!
				pictures.append(item)
			}
		}
	}
	
	
	//MARK: Buttons
	@IBAction func rollButton(_ sender: UIButton) {
		var text = "Default message"
		cardView.image = UIImage(named: pictures[0])
		print("roll " + " \(pictures[0])")
		//		submit(image: cardView.image!)
		
		
		
		//1. Create the alert controller.
		let alert = UIAlertController(title: "Some Title", message: "Enter a text", preferredStyle: .alert)
		
		//2. Add the text field. You can configure it however you need.
		alert.addTextField { (textField) in
			textField.text = "Some default text"
		}
		
		// 3. Grab the value from the text field, and print it when the user clicks OK.
		alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { [weak alert] (_) in
			let textField = alert?.textFields![0] // Force unwrapping because we know it exists.
			text = (textField?.text)!
			print("Text field: \(String(describing: textField?.text))")
		}))
		
		// 4. Present the alert.
		self.present(alert, animated: true, completion: nil)
		submit(sender: text)
	}
	@IBAction func buyButton(_ sender: UIButton) {
		cardView.image = UIImage(named: pictures[1])
		print("buy " + " \(pictures[1])")
	}
	@IBAction func sellButton(_ sender: Any) {
		cardView.image = UIImage(named: pictures[2])
		print("sell " + " \(pictures[2])")
	}
	@IBAction func buildButton(_ sender: Any) {
		cardView.image = UIImage(named: pictures[3])
		print("trade " + " \(pictures[3])")
	}
	@IBAction func demolishButton(_ sender: Any) {
		cardView.image = UIImage(named: pictures[4])
		print("drawCard " + " \(pictures[4])")
	}
	@IBAction func statsButton(_ sender: Any) {
		cardView.image = UIImage(named: pictures[5])
		print("stats " + " \(pictures[5])")
	}
	
	
	override func didReceiveMemoryWarning() {
		super.didReceiveMemoryWarning()
		// Dispose of any resources that can be recreated.
	}
	
	
	
	
	//	func submit(image: UIImage) {
	//		let session = URLSession(configuration: URLSessionConfiguration.default)
	//
	//		guard let url = URL(string: "http://137.43.13.101:50000/") else { return }
	//
	//		var request = URLRequest(url: url)
	//		request.httpMethod = "POST"
	//		request.httpBody = Data(base64Encoded: pictures[0])
	//
	//		session.dataTask(with: request) { (data, response, error) in
	//
	//			if let error = error {
	//				print("Something went wrong: \(error)")
	//			}
	//			}.resume()
	//	}
	
	
	
	
	
	func submit(sender: String) {
		let session = URLSession(configuration: URLSessionConfiguration.default)
		
		guard let url = URL(string: "http://193.1.166.14:50000/") else { return }
		
		var request = URLRequest(url: url)
		request.httpMethod = sender
		
		session.dataTask(with: request) { (data, response, error) in
			
			if let error = error {
				print("Something went wrong: \(error)")
			}
			}.resume()
	}
}


