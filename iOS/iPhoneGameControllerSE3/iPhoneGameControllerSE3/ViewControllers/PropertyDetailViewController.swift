//
//  PropertyDetailViewController.swift
//  iPhoneGameControllerSE3
//
//  Created by Conal O'Neill on 11/02/2018.
//  Copyright © 2018 Conal O'Neill. All rights reserved.
//

import Foundation
import UIKit

class PropertyDetailViewController: UIViewController {
	@IBOutlet weak var propertiesDetails: UILabel!
	
	var selectedProperty: String?
	
	override func viewDidLoad() {
		propertiesDetails.text = "Placeholder text"
	}
}
