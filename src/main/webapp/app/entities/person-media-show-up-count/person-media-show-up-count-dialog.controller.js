(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonMediaShowUpCountDialogController', PersonMediaShowUpCountDialogController);

    PersonMediaShowUpCountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonMediaShowUpCount', 'Person'];

    function PersonMediaShowUpCountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonMediaShowUpCount, Person) {
        var vm = this;

        vm.personMediaShowUpCount = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
                vm.people = [];
        vm.searchPersonWithKeyword = searchPersonWithKeyword;
        function searchPersonWithKeyword(keyword) {

            if (keyword) {

                Person.loadAllByPersonName({
                    page: 0,
                    size: 10,
                    name: keyword
                }, onSuccess, onError);
            }

            function onSuccess(data) {
                vm.people = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personMediaShowUpCount.id !== null) {
                PersonMediaShowUpCount.update(vm.personMediaShowUpCount, onSaveSuccess, onSaveError);
            } else {
                PersonMediaShowUpCount.save(vm.personMediaShowUpCount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personMediaShowUpCountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.showUpDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
