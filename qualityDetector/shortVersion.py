import os.path
from keras.preprocessing import image
import numpy as np
from keras.models import load_model


#Load the model
new_model = load_model(os.path.join('models', 'fruitsRottenOrNot.h5'))

def test_image(image_path):
    test_image = image.load_img(image_path, target_size=(100, 100))
    test_image = image.img_to_array(test_image)
    test_image/=255
    test_image = np.expand_dims(test_image, axis=0)
    result = new_model.predict(test_image)
    predictedClassIndex = np.argmax(result)
    if predictedClassIndex == 0:
        return "Fresh Apple"
    elif predictedClassIndex == 1:
        return "Fresh Banana"
    elif predictedClassIndex == 2:
        return "Fresh Orange"
    elif predictedClassIndex == 3:
        return "Rotten Apple"
    elif predictedClassIndex == 4:
        return "Rotten Banana"
    elif predictedClassIndex == 5:
        return "Rotten Orange"
    else:
        return "Unknown"

print(test_image("Testing_images/Rapple.jpg"))