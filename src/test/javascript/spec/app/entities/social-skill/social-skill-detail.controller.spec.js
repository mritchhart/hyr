'use strict';

describe('Controller Tests', function() {

    describe('Social_skill Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSocial_skill, MockPoint_entry, MockStudent_social_skill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSocial_skill = jasmine.createSpy('MockSocial_skill');
            MockPoint_entry = jasmine.createSpy('MockPoint_entry');
            MockStudent_social_skill = jasmine.createSpy('MockStudent_social_skill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Social_skill': MockSocial_skill,
                'Point_entry': MockPoint_entry,
                'Student_social_skill': MockStudent_social_skill
            };
            createController = function() {
                $injector.get('$controller')("Social_skillDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hopeRanchLearningAcademyApp:social_skillUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
