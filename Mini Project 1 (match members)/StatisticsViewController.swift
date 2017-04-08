//
//  StatisticsViewController.swift
//  Mini Project 1 (match members)
//
//  Created by Boris Yue on 2/10/17.
//  Copyright © 2017 Boris Yue. All rights reserved.
//

import UIKit

class StatisticsViewController: UIViewController {

    @IBOutlet weak var streak: UILabel!
    var longestStreak = ""
    var lastThree: [String] = []
    
    @IBOutlet weak var firstLabel: UILabel!
    
    @IBOutlet weak var secondLabel: UILabel!
    @IBOutlet weak var thirdLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        var labels = [firstLabel, secondLabel, thirdLabel]
        streak.text = longestStreak
        for i in 0..<lastThree.count {
            labels[i]?.text = lastThree[i]
        }
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
