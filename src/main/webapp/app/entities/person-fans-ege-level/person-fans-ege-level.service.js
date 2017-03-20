(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFansEgeLevel', PersonFansEgeLevel);

    PersonFansEgeLevel.$inject = ['$resource'];

    function PersonFansEgeLevel ($resource) {
        var resourceUrl =  'api/person-fans-ege-levels/:id';
        var findByPersonUrl =  'api/person-fans-ege-levels/person/:id';
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
