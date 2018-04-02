//
//  PropertiesViewController.swift
//  iPhoneGameControllerSE3
//
//  Created by Conal O'Neill on 11/02/2018.
//  Copyright © 2018 Conal O'Neill. All rights reserved.
//

import UIKit

class PropertiesTableViewController : UITableViewController {
	
	var properties = [String]()
	
	override func viewDidLoad() {
		loadProperties()
	}
	
	//MARK: Table View Methods
	override func numberOfSections(in tableView: UITableView) -> Int {
		return properties.count
	}
	
	override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = tableView.dequeueReusableCell(withIdentifier: "PropertyCell", for: indexPath)
		cell.textLabel?.text = properties[indexPath.row]
		return cell
	}
	
	override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		// 1: try loading the "Detail" view controller and typecasting it to be DetailViewController
		if let vc = storyboard?.instantiateViewController(withIdentifier: "PropertyDetail") as? PropertyDetailViewController {
			// 2: success! Set its selectedImage property
			vc.selectedProperty = properties[indexPath.row]
			
			// 3: now push it onto the navigation controller
			navigationController?.pushViewController(vc, animated: true)
		}
	}
	
	
	
	//MARK: Private Methods
	private func loadProperties() {
		let file = "properties.txt" //this is the file. we will write to and read from it
		
		if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
			
			let fileURL = dir.appendingPathComponent(file)

			//reading
			do {
				if let path = Bundle.main.path(forResource: "properties", ofType: "txt"){
					let data = try String(contentsOfFile:path, encoding: String.Encoding.utf8)
					let myStrings = data.components(separatedBy: NSCharacterSet.newlines)
					for string in myStrings {
						properties.append(string)
					}
					
				}
			}
			catch {print("Failed reading from URL: \(fileURL), Error: " + error.localizedDescription)}
		}
	}
}
