(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFanSex', PersonFanSex);

    PersonFanSex.$inject = ['$resource'];

    function PersonFanSex ($resource) {
        var resourceUrl =  'api/person-fan-sexes/:id';
        var findByPersonUrl =  'api/person-fan-sexes/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
