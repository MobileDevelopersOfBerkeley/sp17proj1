//
//  ViewController.swift
//  nameGame
//
//  Created by Levi Walsh on 2/9/17.
//  Copyright © 2017 Levi Walsh. All rights reserved.
//

import UIKit
import Foundation

class ViewController: UIViewController {
    
    @IBOutlet weak var score: UILabel!
    @IBOutlet weak var timer: UILabel!
    @IBOutlet weak var memberPhoto: UIImageView!
    @IBOutlet weak var starter: UIButton!
    var time: Timer!
    
    //Start and Stop button that toggles
    @IBAction func startButton(_ sender: UIButton) {
        
        score.text = "0"
        if sender.titleLabel?.text == "Start" {
            time = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(update), userInfo: nil, repeats: true)
            score.text = String(currentScore)
            timer.text = String(currentTime)
            score.isEnabled = true
            timer.isEnabled = true
            memberPhoto.isHidden = false
            load()
            for button in buttons {
                button.isEnabled = true
                button.tintColor = UIColor.blue
            }
            sender.setTitle("Stop", for: .normal)
        }
        else {
            sender.setTitle("Start", for: .normal)
            score.text = "0"
            timer.text = "5"
            time.invalidate()
            currentTime = 5
            if currentScore > maxScore {
                maxScore = currentScore
            }
            currentScore = 0
            timer.isEnabled = false
            score.isEnabled = false
            memberPhoto.isHidden = true
            for button in buttons {
                button.isEnabled = false
                button.tintColor = UIColor.clear
            }
        }
    }
    
    let members: [String] = ["Jessica Cherny", "Kevin Jiang", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla", "Akkshay Khoslaa", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Ashwin Vaidyanathan", "Cody Hsieh", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"]
    
    var currentTime: Int = 5
    var currentScore: Int = 0
    var correctButton: UIButton? = nil
    var onHold = false
    var maxScore = 0
    var lastAnswers: [String] = ["None", "None", "None"]
    var comingBack = false
    
    func update() {
        if (onHold){
            currentTime = 5
        } else {
            if currentTime > 0 {
                timer.text = String(currentTime)
                currentTime -= 1
            }
            else {
                currentTime = 5
                timer.text = String(currentTime)
                if currentScore > maxScore {
                    maxScore = currentScore
                }
                currentScore = 0
                score.text = "0"
                correctButton?.tintColor = UIColor.green
                onHold = true
                DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(2), execute: {
                    self.correctButton?.tintColor = UIColor.blue
                    self.onHold = false
                    self.load()
                    self.lastAnswers[2] = self.lastAnswers[1]
                    self.lastAnswers[1] = self.lastAnswers[0]
                    self.lastAnswers[0] = "Timed Out"
                    
                })
            }
        }
    }
    //loads new photo and names
    func load() {
        var memberIndex: Int = Int(arc4random_uniform(44))
        var member: String = members[memberIndex]
        member = member.lowercased()
        member = member.replacingOccurrences(of: " ", with: "")
        memberPhoto.image = UIImage(named: member)
        let correctIndex: Int = Int(arc4random_uniform(4))
        let correct = buttons[correctIndex]
        var person = members[memberIndex]
        correct.setTitle(person, for: .normal)
        var usedNames: [String] = [person]
        correctButton = correct
        //makes sure each name choice is unique
        for button in buttons{
            if button != correct {
                while usedNames.contains(person) {
                    memberIndex = Int(arc4random_uniform(44))
                    person = members[memberIndex]
                }
                button.setTitle(person, for: .normal)
                usedNames.append(person)
            }
        }
    }
    
    @IBAction func buttonPressed(_ sender: UIButton) {
        onHold = true
        if sender == correctButton {
            currentScore += 1
            if currentScore > maxScore {
                maxScore = currentScore
            }
            score.text = String(currentScore)
            sender.tintColor = UIColor.green
            lastAnswers[2] = lastAnswers[1]
            lastAnswers[1] = lastAnswers[0]
            lastAnswers[0] = "Correct"
        }
        else {
            if currentScore > maxScore {
                maxScore = currentScore
            }
            currentScore = 0
            score.text = String(currentScore)
            sender.tintColor  = UIColor.red
            correctButton?.tintColor  = UIColor.green
            lastAnswers[2] = lastAnswers[1]
            lastAnswers[1] = lastAnswers[0]
            lastAnswers[0] = "Wrong"
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(2), execute: {
            self.onHold = false
            self.correctButton?.tintColor = UIColor.blue
            sender.tintColor  = UIColor.blue
            self.load()
        })
    }
    

    @IBOutlet var buttons: [UIButton]!

    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let controller = segue.destination as? SecondViewController {
            controller.maxi = String(maxScore)
            controller.lastThree = lastAnswers
            controller.backScore = currentScore
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if comingBack {
            startButton(starter)
            comingBack = false
        }
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}

