'use strict';

describe('Controller Tests', function() {

    describe('Point_entry Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPoint_entry, MockTeacher, MockStudent, MockSocial_skill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPoint_entry = jasmine.createSpy('MockPoint_entry');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockStudent = jasmine.createSpy('MockStudent');
            MockSocial_skill = jasmine.createSpy('MockSocial_skill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Point_entry': MockPoint_entry,
                'Teacher': MockTeacher,
                'Student': MockStudent,
                'Social_skill': MockSocial_skill
            };
            createController = function() {
                $injector.get('$controller')("Point_entryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hopeRanchLearningAcademyApp:point_entryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
