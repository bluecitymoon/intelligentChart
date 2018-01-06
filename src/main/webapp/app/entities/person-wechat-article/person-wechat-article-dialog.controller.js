(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonWechatArticleDialogController', PersonWechatArticleDialogController);

    PersonWechatArticleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonWechatArticle', 'Person'];

    function PersonWechatArticleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonWechatArticle, Person) {
        var vm = this;

        vm.personWechatArticle = entity;
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
            if (vm.personWechatArticle.id !== null) {
                PersonWechatArticle.update(vm.personWechatArticle, onSaveSuccess, onSaveError);
            } else {
                PersonWechatArticle.save(vm.personWechatArticle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personWechatArticleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
