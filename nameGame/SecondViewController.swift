//
//  SecondViewController.swift
//  nameGame
//
//  Created by Levi Walsh on 2/10/17.
//  Copyright © 2017 Levi Walsh. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {
    @IBOutlet weak var maxValue: UILabel!
    @IBOutlet weak var lastButton: UILabel!
    @IBOutlet weak var sLastButton: UILabel!
    @IBOutlet weak var tLastButton: UILabel!
    
    var maxi = ""
    var lastThree: [String] = ["None", "None", "None"]
    var backScore: Int = 0
    

    override func viewDidLoad() {
        super.viewDidLoad()
        maxValue.text = "Maximum: " + maxi
        lastButton.text = "Last: " + lastThree[0]
        sLastButton.text = "Two ago: " + lastThree[1]
        tLastButton.text = "Three ago: " + lastThree[2]
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let controller = segue.destination as? ViewController {
            controller.comingBack = true
            controller.currentScore = backScore
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
